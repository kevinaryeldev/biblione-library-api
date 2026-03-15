package com.biblione.library_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(String message, int status) {
        super(message);
        this.status = HttpStatus.resolve(status) != null ? HttpStatus.resolve(status) : HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}