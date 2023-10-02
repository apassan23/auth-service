package com.phoenix.authservice.transform;

import com.phoenix.authservice.model.ValidateTokenResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateTokenResponseBuilder {

    public static ValidateTokenResponse build(Collection<? extends GrantedAuthority> authorities, Boolean isAuthenticated, User user) {
        ValidateTokenResponse validateTokenResponse = new ValidateTokenResponse();
        validateTokenResponse.setAuthorities(authorities);
        validateTokenResponse.setIsAuthenticated(isAuthenticated);
        validateTokenResponse.setName(user.getUsername());
        validateTokenResponse.setAccountNonExpired(user.isAccountNonExpired());
        validateTokenResponse.setIsCredentialsNonExpired(user.isCredentialsNonExpired());
        return validateTokenResponse;
    }
}
