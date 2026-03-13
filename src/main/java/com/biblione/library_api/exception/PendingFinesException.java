package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class PendingFinesException extends BusinessException {
    public PendingFinesException() {
        super("Leitor possui multas pendentes. Regularize sua situação.", HttpStatus.FORBIDDEN);
    }
}