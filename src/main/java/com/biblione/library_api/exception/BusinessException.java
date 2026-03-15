package com.biblione.library_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

    private final HttpStatus status;

    public BusinessException(String message, int status) {
        super(message);
        this.status = status;
    }
}