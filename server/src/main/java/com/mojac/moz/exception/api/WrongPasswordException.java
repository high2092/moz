package com.mojac.moz.exception.api;

public class WrongPasswordException extends ApiException {

    public WrongPasswordException() {
        this.error = Error.WRONG_PASSWORD;
    }
}
