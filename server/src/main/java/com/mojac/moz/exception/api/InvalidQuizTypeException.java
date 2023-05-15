package com.mojac.moz.exception.api;

public class InvalidQuizTypeException extends ApiException {

    public InvalidQuizTypeException() {
        this.error = Error.INVALID_QUIZ_TYPE;
    }
}
