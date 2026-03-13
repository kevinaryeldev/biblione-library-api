package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class LoanLimitExceededException extends BusinessException {
    public LoanLimitExceededException(int limit) {
        super("Limite de empréstimos simultâneos atingido: " + limit, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}