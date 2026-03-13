package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class LoanNotFoundException extends BusinessException {
    public LoanNotFoundException(String id) {
        super("Empréstimo não encontrado: " + id, HttpStatus.NOT_FOUND);
    }
}