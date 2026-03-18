package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_UNPROCESSABLE_CONTENT;

public class CopyNotAvailableException extends BusinessException {
    public CopyNotAvailableException() {
        super("Nenhum exemplar disponível para empréstimo.", SC_UNPROCESSABLE_CONTENT);
    }
}
