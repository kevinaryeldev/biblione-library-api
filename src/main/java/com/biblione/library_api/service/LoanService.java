package com.biblione.library_api.service;

import com.biblione.library_api.entity.*;
import com.biblione.library_api.enums.CopyStatus;
import com.biblione.library_api.enums.LoanStatus;
import com.biblione.library_api.exception.*;
import com.biblione.library_api.kafka.event.LoanEventData;
import com.biblione.library_api.kafka.producer.LibraryEventProducer;
import com.biblione.library_api.repository.CopyRepository;
import com.biblione.library_api.repository.LoanRepository;
import com.biblione.library_api.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.apache.hc.core5.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final CopyRepository copyRepository;
    private final ReaderService readerService;
    private final LoanPolicyService loanPolicyService;
    private final LibraryEventProducer eventProducer;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;


    public Page<Loan> findAll(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }

    public Loan findById(UUID id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id.toString()));
    }

    public List<Loan> findActiveByReader(UUID readerId) {
        return loanRepository.findByReaderIdAndStatus(readerId, LoanStatus.ACTIVE);
    }

    @Transactional
    public Loan create(UUID bookId, UUID readerId, UUID registeredBy) {
        LoanPolicy policy = loanPolicyService.findActive();
        Reader reader = readerService.findById(readerId);

        if (reader.isBlocked()) {
            throw new ReaderBlockedException();
        }

        long activeLoans = loanRepository.countByReaderIdAndStatus(readerId, LoanStatus.ACTIVE);
        if (activeLoans >= policy.getMaxSimultaneous()) {
            throw new LoanLimitExceededException(policy.getMaxSimultaneous());
        }

        Copy copy = copyRepository
                .findByBookIdAndStatus(bookId, CopyStatus.AVAILABLE)
                .stream()
                .findFirst()
                .orElseThrow(CopyNotAvailableException::new);

        copy.setStatus(CopyStatus.LOANED);
        copyRepository.save(copy);

        Loan loan = Loan.builder()
                .copy(copy)
                .reader(reader)
                .loanedAt(OffsetDateTime.now())
                .dueDate(LocalDate.now().plusDays(policy.getLoanDays()))
                .status(LoanStatus.ACTIVE)
                .registeredBy(registeredBy)
                .build();

        loanRepository.save(loan);

        eventProducer.publishLoanCreated(buildLoanEventData(loan));
        return loan;
    }

    @Transactional
    public Loan returnLoan(UUID loanId) {
        Loan loan = findById(loanId);

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new BusinessException(
                    "Empréstimo já foi devolvido.",
                    SC_UNPROCESSABLE_CONTENT);
        }

        loan.setReturnedAt(OffsetDateTime.now());
        loan.setStatus(LoanStatus.RETURNED);

        Copy copy = loan.getCopy();
        copy.setStatus(CopyStatus.AVAILABLE);
        copyRepository.save(copy);

        loanRepository.save(loan);
        eventProducer.publishLoanReturned(buildLoanEventData(loan));

        // notifica próximo na fila se houver reserva
        reservationService.notifyNextInQueue(copy.getBook().getId());

        return loan;
    }

    @Transactional
    public Loan renew(UUID loanId) {
        Loan loan = findById(loanId);
        LoanPolicy policy = loanPolicyService.findActive();

        if (!loan.canRenew()) {
            throw new RenewalNotAllowedException();
        }

        // verifica se existe reserva aguardando para este livro
        UUID bookId = loan.getCopy().getBook().getId();
        boolean hasWaitingReservation = reservationRepository
                .findWaitingByBookIdOrdered(bookId)
                .stream()
                .anyMatch(r -> !r.getReader().getId().equals(loan.getReader().getId()));
        if (hasWaitingReservation) {
            throw new RenewalNotAllowedException();
        }
        loan.setRenewedAt(OffsetDateTime.now());
        loan.setDueDate(LocalDate.now().plusDays(policy.getLoanDays()));
        loan.setStatus(LoanStatus.RENEWED);
        loanRepository.save(loan);
        eventProducer.publishLoanRenewed(buildLoanEventData(loan));
        return loan;
    }

    @Transactional
    public void processOverdueLoans() {
        List<Loan> overdueLoans = loanRepository.findOverdueLoans(LocalDate.now());
        overdueLoans.forEach(loan -> {
            loan.setStatus(LoanStatus.OVERDUE);
            loanRepository.save(loan);
            eventProducer.publishLoanOverdue(buildLoanEventData(loan));
        });
    }

    private LoanEventData buildLoanEventData(Loan loan) {
        return LoanEventData.builder()
                .loanId(loan.getId())
                .copyId(loan.getCopy().getId())
                .bookId(loan.getCopy().getBook().getId())
                .bookTitle(loan.getCopy().getBook().getTitle())
                .readerId(loan.getReader().getId())
                .readerName(loan.getReader().getName())
                .readerEmail(loan.getReader().getEmail())
                .registeredBy(loan.getRegisteredBy())
                .loanedAt(loan.getLoanedAt())
                .dueDate(loan.getDueDate())
                .returnedAt(loan.getReturnedAt())
                .renewedAt(loan.getRenewedAt())
                .build();
    }
}