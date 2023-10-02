package com.phoenix.authservice.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    private final TokenDetails tokenDetails;

    public JwtConfig(TokenDetails tokenDetails) {
        this.tokenDetails = tokenDetails;
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(tokenDetails.getSigningKey().getBytes())
                .build();
    }
}
