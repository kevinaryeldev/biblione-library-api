package com.biblione.library_api.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record FineResponse(
        UUID id,
        UUID loanId,
        UUID readerId,
        String readerName,
        Short daysOverdue,
        BigDecimal amount,
        OffsetDateTime paidAt,
        OffsetDateTime waivedAt,
        OffsetDateTime createdAt
) {}
