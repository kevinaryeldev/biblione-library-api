package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;

public class ReservationNotFoundException extends BusinessException {
    public ReservationNotFoundException(String id) {
        super("Reserva não encontrada: " + id, SC_NOT_FOUND);
    }
}
