package com.biblione.library_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "loan_policies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(name = "loan_days", nullable = false)
    private Short loanDays;

    @Column(name = "max_renewals", nullable = false)
    private Short maxRenewals;

    @Column(name = "max_simultaneous", nullable = false)
    private Short maxSimultaneous;

    @Column(name = "fine_per_day", nullable = false, precision = 6, scale = 2)
    private BigDecimal finePerDay;

    @Column(name = "reservation_hold_days", nullable = false)
    private Short reservationHoldDays;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}