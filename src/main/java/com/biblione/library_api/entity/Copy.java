package com.biblione.library_api.entity;

import com.biblione.library_api.enums.CopyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "copies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false, unique = true, length = 40)
    private String barcode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CopyStatus status;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    @Column(name = "discard_date")
    private LocalDate discardDate;

    @Column(name = "discard_reason")
    private String discardReason;

    @Column(columnDefinition = "text")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}