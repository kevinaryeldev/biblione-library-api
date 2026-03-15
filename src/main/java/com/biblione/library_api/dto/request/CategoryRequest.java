package com.biblione.library_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CategoryRequest(
        @NotBlank @Size(max = 80) String name,
        UUID parentId
) {}
