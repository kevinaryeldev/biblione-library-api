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
public class CopyEventData {
    private UUID copyId;
    private UUID bookId;
    private String bookTitle;
    private String barcode;
    private String discardReason;
    private UUID registeredBy;
    private OffsetDateTime acquisitionDate;
    private OffsetDateTime occurredAt;
}