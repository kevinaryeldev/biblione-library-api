package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_UNPROCESSABLE_CONTENT;

public class LoanLimitExceededException extends BusinessException {
    public LoanLimitExceededException(int limit) {
        super("Limite de empréstimos simultâneos atingido: " + limit, SC_UNPROCESSABLE_CONTENT);
    }
}
