package com.phoenix.authservice.exception;

import com.phoenix.authservice.exception.enums.AuthError;
import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException {
    private final AuthError authError;

    public AuthServiceException(String message, AuthError authError) {
        super(message);
        this.authError = authError;
    }

    public AuthServiceException(String message, Throwable cause, AuthError authError) {
        super(message, cause);
        this.authError = authError;
    }

    public AuthServiceException(Throwable cause, AuthError authError) {
        super(cause);
        this.authError = authError;
    }
}
