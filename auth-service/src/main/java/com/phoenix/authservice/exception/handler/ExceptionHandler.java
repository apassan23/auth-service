package com.phoenix.authservice.exception.handler;

import com.phoenix.authservice.exception.AuthServiceBadRequestException;
import com.phoenix.authservice.exception.AuthServiceException;
import com.phoenix.authservice.exception.AuthServiceInternalException;
import com.phoenix.authservice.exception.AuthServiceNotAuthorizedException;
import com.phoenix.authservice.exception.enums.AuthError;
import com.phoenix.authservice.exception.response.AuthServiceError;
import com.phoenix.authservice.exception.response.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class, AuthServiceException.class, AuthServiceInternalException.class})
    public AuthServiceError handleException(Exception exception) {
        AuthServiceError authServiceError = new AuthServiceError();
        authServiceError.setErrorCode(AuthError.SERVER_ERROR);
        authServiceError.setErrorType(ErrorType.INTERNAL_SERVER_ERROR);
        authServiceError.setErrorDescription(exception.getMessage());
        return authServiceError;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler({AuthServiceBadRequestException.class})
    public AuthServiceError handleBadRequestException(AuthServiceBadRequestException exception) {
        AuthServiceError authServiceError = new AuthServiceError();
        authServiceError.setErrorCode(exception.getAuthError());
        authServiceError.setErrorType(ErrorType.BAD_REQUEST);
        List<String> errors = Arrays.asList(exception.getMessage());
        Optional.ofNullable(exception.getCause()).flatMap(cause -> Optional.of(cause.getMessage())).ifPresent(errors::add);
        authServiceError.setErrorDescription(String.join("::", errors));
        return authServiceError;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @org.springframework.web.bind.annotation.ExceptionHandler({AuthenticationException.class, AuthServiceNotAuthorizedException.class})
    public AuthServiceError handleNotAuthorizedException(Exception exception) {
        AuthServiceError authServiceError = new AuthServiceError();
        authServiceError.setErrorCode(AuthError.NOT_AUTHORIZED_ERROR);
        authServiceError.setErrorType(ErrorType.UNAUTHORIZED);
        List<String> errors = Arrays.asList(exception.getMessage());
        Optional.ofNullable(exception.getCause()).flatMap(cause -> Optional.of(cause.getMessage())).ifPresent(errors::add);
        authServiceError.setErrorDescription(String.join("::", errors));
        return authServiceError;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @org.springframework.web.bind.annotation.ExceptionHandler({AccessDeniedException.class})
    public AuthServiceError handleAccessDeniedException(AccessDeniedException exception) {
        AuthServiceError authServiceError = new AuthServiceError();
        authServiceError.setErrorCode(AuthError.NOT_AUTHORIZED_ERROR);
        authServiceError.setErrorType(ErrorType.UNAUTHORIZED);
        List<String> errors = Arrays.asList(exception.getMessage());
        Optional.ofNullable(exception.getCause()).flatMap(cause -> Optional.of(cause.getMessage())).ifPresent(errors::add);
        authServiceError.setErrorDescription(String.join("::", errors));
        return authServiceError;
    }
}
