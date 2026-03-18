package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;

public class ReaderNotFoundException extends BusinessException {
    public ReaderNotFoundException(String id) {
        super("Leitor não encontrado: " + id, SC_NOT_FOUND);
    }
}
