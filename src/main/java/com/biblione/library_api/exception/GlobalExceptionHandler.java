package com.biblione.library_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.apache.hc.core5.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.hc.core5.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {
        log.warn("Business exception at {}: {}", request.getRequestURI(), ex.getMessage());
        int code = ex.getStatusCode();
        HttpStatus resolved = HttpStatus.resolve(code);
        String name = resolved != null ? resolved.name() : String.valueOf(code);
        String phrase = resolved != null ? resolved.getReasonPhrase() : String.valueOf(code);
        return ResponseEntity
                .status(code)
                .body(ErrorResponse.of(name, phrase, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorResponse.ErrorDetail> details = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new ErrorResponse.ErrorDetail(
                        "VALIDATION_ERROR",
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()
                ))
                .toList();
        log.warn("Validation exception at {}: {}", request.getRequestURI(), details);
        return ResponseEntity
                .status(SC_BAD_REQUEST)
                .body(ErrorResponse.ofValidation(details));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return ResponseEntity
                .status(SC_INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.of(
                        "INTERNAL_SERVER_ERROR",
                        "Erro interno",
                        "Erro interno. Tente novamente mais tarde."
                ));
    }
}
