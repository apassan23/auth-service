package com.phoenix.authservice.exception;

import com.phoenix.authservice.exception.enums.AuthError;

public class AuthServiceBadRequestException extends AuthServiceException {


    public AuthServiceBadRequestException(String message, AuthError authError) {
        super(message, authError);
    }

    public AuthServiceBadRequestException(String message, Throwable cause, AuthError authError) {
        super(message, cause, authError);
    }

    public AuthServiceBadRequestException(Throwable cause, AuthError authError) {
        super(cause, authError);
    }
}
