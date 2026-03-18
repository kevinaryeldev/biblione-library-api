package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;

public class LoanNotFoundException extends BusinessException {
    public LoanNotFoundException(String id) {
        super("Empréstimo não encontrado: " + id, SC_NOT_FOUND);
    }
}
