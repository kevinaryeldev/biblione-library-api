package com.biblione.library_api.service;

import com.biblione.library_api.entity.Fine;
import com.biblione.library_api.entity.Loan;
import com.biblione.library_api.exception.BusinessException;
import com.biblione.library_api.kafka.event.FineEventData;
import com.biblione.library_api.kafka.producer.LibraryEventProducer;
import com.biblione.library_api.repository.FineRepository;
import com.biblione.library_api.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.hc.core5.http.HttpStatus.SC_UNPROCESSABLE_CONTENT;

@Service
@RequiredArgsConstructor
public class FineService {

    private final FineRepository fineRepository;
    private final LoanRepository loanRepository;
    private final LoanPolicyService loanPolicyService;
    private final LibraryEventProducer eventProducer;

    public List<Fine> findByReader(UUID readerId) {
        return fineRepository.findByReaderId(readerId);
    }

    public List<Fine> findPendingByReader(UUID readerId) {
        return fineRepository.findPendingByReaderId(readerId);
    }

    public List<UUID> findReaderIdsWithPendingFines() {
        return fineRepository.findReaderIdsWithPendingFines();
    }

    @Transactional
    public void processOverdueFines() {
        loanRepository.findOverdueLoans(LocalDate.now())
                .stream()
                .filter(loan -> !fineRepository.existsByLoanId(loan.getId()))
                .forEach(this::createForLoan);
    }

    @Transactional
    public void updatePendingFineAmounts() {
        BigDecimal finePerDay = loanPolicyService.findActive().getFinePerDay();

        fineRepository.findAllPending().forEach(fine -> {
            long daysOverdue = ChronoUnit.DAYS.between(
                    fine.getLoan().getDueDate(), LocalDate.now());
            fine.setDaysOverdue((short) daysOverdue);
            fine.setAmount(finePerDay.multiply(BigDecimal.valueOf(daysOverdue)));
            fineRepository.save(fine);
        });
    }

    @Transactional
    public Fine payFine(UUID fineId) {
        Fine fine = findById(fineId);

        if (!fine.isPending()) {
            throw new BusinessException(
                    "Multa já foi paga ou dispensada.",
                    SC_UNPROCESSABLE_CONTENT);
        }

        fine.setPaidAt(OffsetDateTime.now());
        fineRepository.save(fine);

        eventProducer.publishFinePaid(FineEventData.builder()
                .fineId(fine.getId())
                .loanId(fine.getLoan().getId())
                .readerId(fine.getReader().getId())
                .readerName(fine.getReader().getName())
                .readerEmail(fine.getReader().getEmail())
                .daysOverdue(fine.getDaysOverdue())
                .amount(fine.getAmount())
                .build());

        return fine;
    }

    @Transactional
    public Fine waiveFine(UUID fineId) {
        Fine fine = findById(fineId);

        if (!fine.isPending()) {
            throw new BusinessException(
                    "Multa já foi paga ou dispensada.",
                    SC_UNPROCESSABLE_CONTENT);
        }

        fine.setWaivedAt(OffsetDateTime.now());
        fineRepository.save(fine);
        return fine;
    }

    @Transactional
    public void createForLoan(Loan loan) {
        if (fineRepository.existsByLoanId(loan.getId())) {
            return;
        }

        long daysOverdue = ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
        BigDecimal finePerDay = loanPolicyService.findActive().getFinePerDay();
        BigDecimal amount = finePerDay.multiply(BigDecimal.valueOf(daysOverdue));

        Fine fine = Fine.builder()
                .loan(loan)
                .reader(loan.getReader())
                .daysOverdue((short) daysOverdue)
                .amount(amount)
                .build();

        fineRepository.save(fine);

        eventProducer.publishFineCreated(FineEventData.builder()
                .fineId(fine.getId())
                .loanId(loan.getId())
                .readerId(loan.getReader().getId())
                .readerName(loan.getReader().getName())
                .readerEmail(loan.getReader().getEmail())
                .daysOverdue((short) daysOverdue)
                .amount(amount)
                .build());
    }

    private Fine findById(UUID fineId) {
        return fineRepository.findById(fineId)
                .orElseThrow(() -> new BusinessException(
                        "Multa não encontrada: " + fineId,
                        SC_NOT_FOUND));
    }
}