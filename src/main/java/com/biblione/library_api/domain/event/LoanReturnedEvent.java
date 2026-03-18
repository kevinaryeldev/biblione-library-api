package com.biblione.library_api.domain.event;

import java.util.UUID;

public record LoanReturnedEvent(UUID bookId) {}
