package com.biblione.library_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "fines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Reader reader;

    @Column(name = "days_overdue", nullable = false)
    private Short daysOverdue;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(name = "paid_at")
    private OffsetDateTime paidAt;

    @Column(name = "waived_at")
    private OffsetDateTime waivedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    public boolean isPaid() {
        return paidAt != null;
    }

    public boolean isWaived() {
        return waivedAt != null;
    }

    public boolean isPending() {
        return !isPaid() && !isWaived();
    }
}