package com.mojac.moz.exception.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleApiException(ApiException exception) {
        return handleApiExceptionInternal(exception.error);
    }

    public ResponseEntity<Object> handleApiExceptionInternal(Error error) {
        return ResponseEntity.status(error.getHttpStatus())
                .body(new ErrorResponse(error.getCode(), error.getMessage()));
    }
}

@Getter
@AllArgsConstructor
class ErrorResponse {
    private int errorCode;
    private String message;
}
