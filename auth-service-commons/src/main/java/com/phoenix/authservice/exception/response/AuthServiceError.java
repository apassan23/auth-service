package com.phoenix.authservice.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phoenix.authservice.exception.enums.AuthError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthServiceError {
    @JsonProperty("errorType")
    private ErrorType errorType;

    @JsonProperty("errorCode")
    private AuthError errorCode;

    @JsonProperty("errorDescription")
    private String errorDescription;
}
