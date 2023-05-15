package com.mojac.moz.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {
    ;

    private final String message;
    private final int code;
    private final HttpStatus httpStatus;
}
