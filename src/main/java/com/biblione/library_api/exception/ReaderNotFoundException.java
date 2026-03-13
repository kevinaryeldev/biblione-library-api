package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class ReaderNotFoundException extends BusinessException {
    public ReaderNotFoundException(String id) {
        super("Leitor não encontrado: " + id, HttpStatus.NOT_FOUND);
    }
}