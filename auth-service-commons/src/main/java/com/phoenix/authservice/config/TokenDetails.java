package com.phoenix.authservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TokenDetails {

    @Value("${jwt.token.validity}")
    private Long tokenValidity;

    @Value("${jwt.signing.key}")
    private String signingKey;

    @Value("${jwt.authorities.key}")
    private String authoritiesKey;
}
