package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;

public class ReaderBlockedException extends BusinessException {
    public ReaderBlockedException() {
        super("Leitor bloqueado. Regularize sua situação com a biblioteca.", SC_FORBIDDEN);
    }
}
