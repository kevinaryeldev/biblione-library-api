package com.biblione.library_api.dto.response;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String isbn13,
        String title,
        String subtitle,
        String publisher,
        String edition,
        Short publishYear,
        String language,
        Short pages,
        String synopsis,
        String coverUrl,
        Set<AuthorResponse> authors,
        Set<CategoryResponse> categories,
        OffsetDateTime createdAt
) {}
