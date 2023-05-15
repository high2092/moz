package com.mojac.moz.exception.api;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
    protected Error error;
}
