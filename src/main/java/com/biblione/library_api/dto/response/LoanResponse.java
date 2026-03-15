package com.biblione.library_api.dto.response;

import com.biblione.library_api.enums.LoanStatus;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record LoanResponse(
        UUID id,
        UUID copyId,
        UUID bookId,
        String bookTitle,
        UUID readerId,
        String readerName,
        OffsetDateTime loanedAt,
        LocalDate dueDate,
        OffsetDateTime returnedAt,
        OffsetDateTime renewedAt,
        LoanStatus status,
        UUID registeredBy
) {}
