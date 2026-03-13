package com.biblione.library_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "readers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reader {

    @Id
    private UUID id;

    @Column(nullable = false, length = 254)
    private String email;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "registration_number", length = 40)
    private String registrationNumber;

    @Column(length = 20)
    private String phone;

    @Column(length = 300)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_id", nullable = false)
    private LoanPolicy policy;

    @Column(name = "is_blocked", nullable = false)
    private boolean blocked;

    @Column(name = "blocked_reason")
    private String blockedReason;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}