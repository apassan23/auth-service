package com.phoenix.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return AbstractHttpConfigurer::disable;
    }
}
