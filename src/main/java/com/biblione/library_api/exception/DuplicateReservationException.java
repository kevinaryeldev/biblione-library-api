package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_CONFLICT;

public class DuplicateReservationException extends BusinessException {
    public DuplicateReservationException() {
        super("Já existe uma reserva ativa para este livro.", SC_CONFLICT);
    }
}
