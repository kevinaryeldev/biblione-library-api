package com.biblione.library_api.dto.response;

import com.biblione.library_api.enums.ReservationStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        UUID bookId,
        String bookTitle,
        UUID readerId,
        String readerName,
        ReservationStatus status,
        Short queuePosition,
        OffsetDateTime readyAt,
        OffsetDateTime expiresAt,
        OffsetDateTime createdAt
) {}
