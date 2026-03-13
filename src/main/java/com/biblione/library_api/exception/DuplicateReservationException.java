package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class DuplicateReservationException extends BusinessException {
    public DuplicateReservationException() {
        super("Já existe uma reserva ativa para este livro.", HttpStatus.CONFLICT);
    }
}