package com.biblione.library_api.dto.response;

import java.util.UUID;

public record AuthorResponse(
        UUID id,
        String fullName,
        String bio,
        String nationality
) {}
