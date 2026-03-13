package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class CopyNotAvailableException extends BusinessException {
    public CopyNotAvailableException() {
        super("Nenhum exemplar disponível para empréstimo.", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}