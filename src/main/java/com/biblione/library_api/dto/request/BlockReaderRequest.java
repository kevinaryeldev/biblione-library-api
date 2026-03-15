package com.biblione.library_api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record BlockReaderRequest(
        @NotBlank String reason
) {}
