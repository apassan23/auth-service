package com.phoenix.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;

@Configuration
public class CsrfConfig {

    @Bean
    public Customizer<CsrfConfigurer<HttpSecurity>> csrfConfigurerCustomizer() {
        return AbstractHttpConfigurer::disable;
    }
}
