package com.phoenix.authservice.exception;

import com.phoenix.authservice.exception.enums.AuthError;

public class AuthServiceInternalException extends AuthServiceException {

    public AuthServiceInternalException(String message, AuthError authError) {
        super(message, authError);
    }

    public AuthServiceInternalException(String message, Throwable cause, AuthError authError) {
        super(message, cause, authError);
    }

    public AuthServiceInternalException(Throwable cause, AuthError authError) {
        super(cause, authError);
    }
}
