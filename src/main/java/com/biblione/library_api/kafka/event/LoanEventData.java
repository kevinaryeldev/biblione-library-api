package com.biblione.library_api.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanEventData {

    private String eventType;
    private UUID loanId;
    private UUID copyId;
    private UUID bookId;
    private String bookTitle;
    private UUID readerId;
    private String readerName;
    private String readerEmail;
    private UUID registeredBy;
    private OffsetDateTime loanedAt;
    private LocalDate dueDate;
    private OffsetDateTime returnedAt;
    private OffsetDateTime renewedAt;
    private OffsetDateTime occurredAt;
}