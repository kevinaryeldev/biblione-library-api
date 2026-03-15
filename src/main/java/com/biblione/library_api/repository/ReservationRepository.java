package com.biblione.library_api.repository;

import com.biblione.library_api.entity.Reservation;
import com.biblione.library_api.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByReaderIdAndStatus(UUID readerId, ReservationStatus status);
    boolean existsByReaderIdAndBookIdAndStatusIn(UUID readerId, UUID bookId, List<ReservationStatus> statuses);

    @Query("SELECT r FROM Reservation r WHERE r.book.id = :bookId AND r.status = 'WAITING' ORDER BY r.queuePosition ASC")
    List<Reservation> findWaitingByBookIdOrdered(UUID bookId);

    @Query("SELECT COALESCE(MAX(r.queuePosition), 0) FROM Reservation r WHERE r.book.id = :bookId AND r.status = 'WAITING'")
    Short findMaxQueuePositionByBookId(UUID bookId);

    Optional<Reservation> findByReaderIdAndBookIdAndStatus(UUID readerId, UUID bookId, ReservationStatus status);

    @Query("SELECT r FROM Reservation r WHERE r.status = 'READY' AND r.expiresAt < :now")
    List<Reservation> findExpiredReady(OffsetDateTime now);
}