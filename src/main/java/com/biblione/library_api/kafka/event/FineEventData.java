package com.biblione.library_api.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FineEventData {

    private UUID fineId;
    private UUID loanId;
    private UUID readerId;
    private String readerName;
    private String readerEmail;
    private Short daysOverdue;
    private BigDecimal amount;
    private OffsetDateTime occurredAt;
}