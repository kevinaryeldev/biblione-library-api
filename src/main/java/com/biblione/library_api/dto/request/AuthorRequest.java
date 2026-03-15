package com.biblione.library_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorRequest(
        @NotBlank @Size(max = 200) String fullName,
        String bio,
        @Size(max = 80) String nationality
) {}
