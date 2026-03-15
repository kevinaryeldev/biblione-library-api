package com.biblione.library_api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationRequest(
        @NotNull UUID bookId,
        @NotNull UUID readerId
) {}
