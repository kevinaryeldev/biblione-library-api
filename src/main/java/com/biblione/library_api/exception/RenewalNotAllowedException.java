package com.biblione.library_api.exception;

import static org.apache.hc.core5.http.HttpStatus.SC_UNPROCESSABLE_CONTENT;

public class RenewalNotAllowedException extends BusinessException {
    public RenewalNotAllowedException() {
        super("Renovação não permitida. O empréstimo está em atraso ou já foi renovado.", SC_UNPROCESSABLE_CONTENT);
    }
}
