package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends BusinessException {
    public ReservationNotFoundException(String id) {
        super("Reserva não encontrada: " + id, HttpStatus.NOT_FOUND);
    }
}