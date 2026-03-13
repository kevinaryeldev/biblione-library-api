package com.biblione.library_api.repository;

import com.biblione.library_api.entity.Loan;
import com.biblione.library_api.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
    List<Loan> findByReaderIdAndStatus(UUID readerId, LoanStatus status);
    long countByReaderIdAndStatus(UUID readerId, LoanStatus status);

    @Query("SELECT l FROM Loan l WHERE l.status = 'ACTIVE' AND l.dueDate < :today")
    List<Loan> findOverdueLoans(LocalDate today);
}