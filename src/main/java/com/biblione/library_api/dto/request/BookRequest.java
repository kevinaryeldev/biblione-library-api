package com.biblione.library_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record BookRequest(
        @Size(max = 13) String isbn13,
        @NotBlank @Size(max = 300) String title,
        @Size(max = 300) String subtitle,
        @Size(max = 200) String publisher,
        @Size(max = 40) String edition,
        Short publishYear,
        @Size(max = 5) String language,
        Short pages,
        @Size(max = 500) String coverUrl,
        @NotEmpty Set<String> authors,
        @NotEmpty Set<String> categories
) {}
