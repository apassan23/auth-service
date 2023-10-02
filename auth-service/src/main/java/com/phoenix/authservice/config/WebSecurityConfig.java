package com.phoenix.authservice.config;

import com.phoenix.authservice.filters.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer;
    private final Customizer<CsrfConfigurer<HttpSecurity>> csrfConfigurerCustomizer;
    private final Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> requestMatcherRegistryCustomizer;
    private final Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingConfigurerCustomizer;
    private final Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementConfigurerCustomizer;

    private final JwtAuthFilter authFilter;

    public WebSecurityConfig(Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer, Customizer<CsrfConfigurer<HttpSecurity>> csrfConfigurerCustomizer, Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> requestMatcherRegistryCustomizer, Customizer<ExceptionHandlingConfigurer<HttpSecurity>> exceptionHandlingConfigurerCustomizer, Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementConfigurerCustomizer, JwtAuthFilter authFilter) {
        this.corsConfigurerCustomizer = corsConfigurerCustomizer;
        this.csrfConfigurerCustomizer = csrfConfigurerCustomizer;
        this.requestMatcherRegistryCustomizer = requestMatcherRegistryCustomizer;
        this.exceptionHandlingConfigurerCustomizer = exceptionHandlingConfigurerCustomizer;
        this.sessionManagementConfigurerCustomizer = sessionManagementConfigurerCustomizer;
        this.authFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(this.corsConfigurerCustomizer)
                .csrf(this.csrfConfigurerCustomizer)
                .authorizeHttpRequests(this.requestMatcherRegistryCustomizer)
                .exceptionHandling(this.exceptionHandlingConfigurerCustomizer)
                .sessionManagement(this.sessionManagementConfigurerCustomizer);

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
