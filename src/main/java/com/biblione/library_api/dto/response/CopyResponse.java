package com.biblione.library_api.dto.response;

import com.biblione.library_api.enums.CopyStatus;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CopyResponse(
        UUID id,
        UUID bookId,
        String bookTitle,
        String barcode,
        CopyStatus status,
        LocalDate acquisitionDate,
        LocalDate discardDate,
        String discardReason,
        OffsetDateTime createdAt
) {}
