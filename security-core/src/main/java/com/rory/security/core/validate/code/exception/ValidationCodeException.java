package com.rory.security.core.validate.code.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidationCodeException extends AuthenticationException {

    public ValidationCodeException(String msg, Throwable t) {
        super(msg, t);
    }
    public ValidationCodeException(String msg) {
        super(msg);
    }
}
