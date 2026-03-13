package com.biblione.library_api.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEventData {

    private String eventType;
    private UUID reservationId;
    private UUID bookId;
    private String bookTitle;
    private UUID readerId;
    private String readerName;
    private String readerEmail;
    private Short queuePosition;
    private OffsetDateTime readyAt;
    private OffsetDateTime expiresAt;
    private OffsetDateTime occurredAt;
}