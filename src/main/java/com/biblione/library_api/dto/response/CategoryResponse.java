package com.biblione.library_api.dto.response;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        UUID parentId,
        String parentName
) {}
