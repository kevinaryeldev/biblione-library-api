package com.biblione.library_api.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ReaderResponse(
        UUID id,
        String email,
        String name,
        String registrationNumber,
        String phone,
        String address,
        boolean blocked,
        String blockedReason,
        OffsetDateTime createdAt
) {}
