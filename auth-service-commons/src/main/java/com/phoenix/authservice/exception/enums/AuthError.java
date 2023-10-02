package com.phoenix.authservice.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AuthError {
    BAD_REQUEST("Bad Request."),
    BAD_CREDENTIALS("Bad Credentials."),
    NOT_AUTHORIZED_ERROR("Not Authorized."),
    NO_USER_FOUND("No User Found."),
    USER_ALREADY_EXISTS("User Already Exists."),
    ROLE_ALREADY_EXISTS("Role Already Exists."),
    NO_ROLE_FOUND("No Role Found."),
    SERVER_ERROR("Server Error.");

    private String description;

}
