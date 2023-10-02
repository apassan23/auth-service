package com.phoenix.authservice.exception;

import com.phoenix.authservice.exception.enums.AuthError;

public class AuthServiceNotAuthorizedException extends AuthServiceException {

    public AuthServiceNotAuthorizedException(String message, AuthError authError) {
        super(message, authError);
    }

    public AuthServiceNotAuthorizedException(String message, Throwable cause, AuthError authError) {
        super(message, cause, authError);
    }

    public AuthServiceNotAuthorizedException(Throwable cause, AuthError authError) {
        super(cause, authError);
    }
}
