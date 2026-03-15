package com.biblione.library_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AddCopyRequest(
        @NotBlank @Size(max = 40) String barcode,
        LocalDate acquisitionDate
) {}
