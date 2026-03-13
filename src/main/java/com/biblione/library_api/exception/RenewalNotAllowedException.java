package com.biblione.library_api.exception;

import org.springframework.http.HttpStatus;

public class RenewalNotAllowedException extends BusinessException {
    public RenewalNotAllowedException() {
        super("Renovação não permitida. O empréstimo está em atraso ou já foi renovado.", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}