package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class ReaderBlockedException extends BusinessException {
    public ReaderBlockedException() {
        super("Leitor bloqueado. Regularize sua situação com a biblioteca.", HttpStatus.FORBIDDEN);
    }
}