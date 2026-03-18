package com.biblione.library_api.service;

import com.biblione.library_api.domain.event.LoanReturnedEvent;
import com.biblione.library_api.entity.Copy;
import com.biblione.library_api.entity.Reader;
import com.biblione.library_api.entity.Reservation;
import com.biblione.library_api.enums.CopyStatus;
import com.biblione.library_api.enums.ReservationStatus;
import com.biblione.library_api.exception.BookNotFoundException;
import com.biblione.library_api.exception.BusinessException;
import com.biblione.library_api.exception.DuplicateReservationException;
import com.biblione.library_api.exception.ReservationNotFoundException;
import com.biblione.library_api.kafka.event.ReservationEventData;
import com.biblione.library_api.kafka.producer.LibraryEventProducer;
import com.biblione.library_api.repository.BookRepository;
import com.biblione.library_api.repository.CopyRepository;
import com.biblione.library_api.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.apache.hc.core5.http.HttpStatus.SC_UNPROCESSABLE_CONTENT;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;
    private final ReaderService readerService;
    private final LoanPolicyService loanPolicyService;
    private final LibraryEventProducer eventProducer;

    public List<Reservation> findByReader(UUID readerId) {
        return reservationRepository.findByReaderIdAndStatus(
                readerId, ReservationStatus.WAITING);
    }

    public Reservation findById(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id.toString()));
    }

    @Transactional
    public Reservation create(UUID bookId, UUID readerId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException(bookId.toString());
        }

        boolean alreadyExists = reservationRepository.existsByReaderIdAndBookIdAndStatusIn(
                readerId, bookId,
                List.of(ReservationStatus.WAITING, ReservationStatus.READY));

        if (alreadyExists) {
            throw new DuplicateReservationException();
        }

        Reader reader = readerService.findById(readerId);
        Short queuePosition = reservationRepository.findMaxQueuePositionByBookId(bookId);

        Reservation reservation = Reservation.builder()
                .book(bookRepository.findById(bookId).get())
                .reader(reader)
                .status(ReservationStatus.WAITING)
                .queuePosition((short) (queuePosition + 1))
                .build();

        reservationRepository.save(reservation);

        eventProducer.publishReservationCreated(buildReservationEventData(reservation));
        return reservation;
    }

    @Transactional
    public void cancel(UUID reservationId) {
        Reservation reservation = findById(reservationId);

        if (reservation.getStatus() != ReservationStatus.WAITING &&
                reservation.getStatus() != ReservationStatus.READY) {
            throw new BusinessException(
                    "Reserva não pode ser cancelada.",
                    SC_UNPROCESSABLE_CONTENT);
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    @EventListener
    @Transactional
    public void onLoanReturned(LoanReturnedEvent event) {
        notifyNextInQueue(event.bookId());
    }

    // chamado pela job e pelo evento de devolução
    @Transactional
    public void expireReadyReservations() {
        reservationRepository.findExpiredReady(OffsetDateTime.now())
                .forEach(reservation -> {
                    reservation.setStatus(ReservationStatus.EXPIRED);
                    reservationRepository.save(reservation);

                    copyRepository.findByBookIdAndStatus(
                                    reservation.getBook().getId(), CopyStatus.RESERVED)
                            .stream()
                            .findFirst()
                            .ifPresent(copy -> {
                                copy.setStatus(CopyStatus.AVAILABLE);
                                copyRepository.save(copy);
                            });

                    eventProducer.publishReservationExpired(buildReservationEventData(reservation));
                    notifyNextInQueue(reservation.getBook().getId());
                });
    }

    private void notifyNextInQueue(UUID bookId) {
        reservationRepository.findWaitingByBookIdOrdered(bookId)
                .stream()
                .findFirst()
                .ifPresent(reservation -> {
                    Short holdDays = loanPolicyService.findActive().getReservationHoldDays();
                    reservation.setStatus(ReservationStatus.READY);
                    reservation.setReadyAt(OffsetDateTime.now());
                    reservation.setExpiresAt(OffsetDateTime.now().plusDays(holdDays));
                    reservationRepository.save(reservation);

                    copyRepository.findByBookIdAndStatus(bookId, CopyStatus.AVAILABLE)
                            .stream()
                            .findFirst()
                            .ifPresent(copy -> {
                                copy.setStatus(CopyStatus.RESERVED);
                                copyRepository.save(copy);
                            });

                    eventProducer.publishReservationReady(buildReservationEventData(reservation));
                });
    }

    private ReservationEventData buildReservationEventData(Reservation reservation) {
        return ReservationEventData.builder()
                .reservationId(reservation.getId())
                .bookId(reservation.getBook().getId())
                .bookTitle(reservation.getBook().getTitle())
                .readerId(reservation.getReader().getId())
                .readerName(reservation.getReader().getName())
                .readerEmail(reservation.getReader().getEmail())
                .queuePosition(reservation.getQueuePosition())
                .readyAt(reservation.getReadyAt())
                .expiresAt(reservation.getExpiresAt())
                .build();
    }
}
