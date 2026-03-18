package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_FORBIDDEN;

public class PendingFinesException extends BusinessException {
    public PendingFinesException() {
        super("Leitor possui multas pendentes. Regularize sua situação.", SC_FORBIDDEN);
    }
}
