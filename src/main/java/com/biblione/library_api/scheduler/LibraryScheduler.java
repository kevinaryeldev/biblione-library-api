package com.biblione.library_api.scheduler;

import com.biblione.library_api.service.FineService;
import com.biblione.library_api.service.LoanService;
import com.biblione.library_api.service.ReaderService;
import com.biblione.library_api.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class LibraryScheduler {

    private final LoanService loanService;
    private final FineService fineService;
    private final ReaderService readerService;
    private final ReservationService reservationService;

    @Scheduled(cron = "0 0 1 * * *")
    public void processOverdueLoans() {
        log.info("Starting overdue loans processing...");
        loanService.processOverdueLoans();
        log.info("Overdue loans processed.");
    }

    @Scheduled(cron = "0 10 1 * * *")
    public void processOverdueFines() {
        log.info("Starting overdue fines processing...");
        fineService.processOverdueFines();
        log.info("Overdue fines processed.");
    }

    @Scheduled(cron = "0 20 1 * * *")
    public void updateFineAmounts() {
        log.info("Starting fine amounts update...");
        fineService.updatePendingFineAmounts();
        log.info("Fine amounts updated.");
    }

    @Scheduled(cron = "0 30 1 * * *")
    public void blockReadersWithPendingFines() {
        log.info("Starting reader blocking...");
        List<UUID> readerIds = fineService.findReaderIdsWithPendingFines();
        readerService.blockReadersWithPendingFines(readerIds);
        log.info("Readers blocked: {}", readerIds.size());
    }

    @Scheduled(cron = "0 40 1 * * *")
    public void expireReadyReservations() {
        log.info("Starting reservation expiration...");
        reservationService.expireReadyReservations();
        log.info("Reservations expired.");
    }
}