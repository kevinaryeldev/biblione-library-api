package com.biblione.library_api.entity;

import com.biblione.library_api.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "copy_id", nullable = false)
    private Copy copy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Reader reader;

    @Column(name = "loaned_at", nullable = false)
    private OffsetDateTime loanedAt;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "returned_at")
    private OffsetDateTime returnedAt;

    @Column(name = "renewed_at")
    private OffsetDateTime renewedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

    @Column(name = "registered_by", nullable = false)
    private UUID registeredBy;

    public boolean isOverdue() {
        return returnedAt == null && LocalDate.now().isAfter(dueDate);
    }

    public boolean canRenew() {
        return renewedAt == null && !isOverdue();
    }
}