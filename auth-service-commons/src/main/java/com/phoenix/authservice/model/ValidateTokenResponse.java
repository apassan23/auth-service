package com.phoenix.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenResponse {
    private String name;
    private Collection<? extends GrantedAuthority> authorities;
    private Boolean isAuthenticated;
    private Boolean isCredentialsNonExpired;
    private boolean isAccountNonExpired;
}
