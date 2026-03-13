package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends BusinessException {
    public BookNotFoundException(String id) {
        super("Livro não encontrado: " + id, HttpStatus.NOT_FOUND);
    }
}