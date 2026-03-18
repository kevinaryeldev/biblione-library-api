package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;

public class BookNotFoundException extends BusinessException {
    public BookNotFoundException(String id) {
        super("Livro não encontrado: " + id, SC_NOT_FOUND);
    }
}
