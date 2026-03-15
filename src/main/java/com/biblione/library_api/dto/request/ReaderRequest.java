package com.biblione.library_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record ReaderRequest(
        @NotNull UUID id,
        @NotBlank @Email @Size(max = 254) String email,
        @NotBlank @Size(max = 120) String name,
        @Size(max = 40) String registrationNumber,
        @Size(max = 20) String phone,
        @Size(max = 300) String address
) {}
