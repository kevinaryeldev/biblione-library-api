package com.biblione.library_api.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LoanPolicyResponse(
        UUID id,
        String name,
        Short loanDays,
        Short maxRenewals,
        Short maxSimultaneous,
        BigDecimal finePerDay,
        Short reservationHoldDays,
        boolean active,
        OffsetDateTime createdAt
) {}
