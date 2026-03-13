package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class CopyNotFoundException extends BusinessException {
    public CopyNotFoundException(String id) {
        super("Exemplar não encontrado: " + id, HttpStatus.NOT_FOUND);
    }
}