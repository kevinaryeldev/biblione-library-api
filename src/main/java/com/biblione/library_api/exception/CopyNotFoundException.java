package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_NOT_FOUND;

public class CopyNotFoundException extends BusinessException {
    public CopyNotFoundException(String id) {
        super("Exemplar não encontrado: " + id, SC_NOT_FOUND);
    }
}
