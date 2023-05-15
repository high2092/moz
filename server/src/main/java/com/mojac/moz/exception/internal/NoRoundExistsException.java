package com.mojac.moz.exception.internal;

public class NoRoundExistsException extends InternalException {

    public NoRoundExistsException() {
        super(Error.NO_ROUND_EXISTS.getMessage());
    }
}
