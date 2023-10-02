package com.phoenix.authservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.authservice.exception.enums.AuthError;
import com.phoenix.authservice.exception.response.AuthServiceError;
import com.phoenix.authservice.exception.response.ErrorType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
public class ExceptionHandlingConfig {

    private final ObjectMapper objectMapper;

    public ExceptionHandlingConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingConfigurerCustomizer() {
        return httpSecurityExceptionHandlingConfigurer -> {
            httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(authenticationEntryPoint());
        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            AuthServiceError authServiceError = new AuthServiceError();
            authServiceError.setErrorCode(AuthError.NOT_AUTHORIZED_ERROR);
            authServiceError.setErrorType(ErrorType.UNAUTHORIZED);
            List<String> errors = Arrays.asList(authException.getMessage());
            Optional.ofNullable(authException.getCause()).flatMap(cause -> Optional.of(cause.getMessage())).ifPresent(errors::add);
            authServiceError.setErrorDescription(String.join("::", errors));
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(objectMapper.writeValueAsString(authServiceError));
        };
    }
}
