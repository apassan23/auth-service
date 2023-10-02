package com.phoenix.authservice.service.impl;

import com.phoenix.authservice.exception.AuthServiceException;
import com.phoenix.authservice.exception.AuthServiceInternalException;
import com.phoenix.authservice.exception.AuthServiceNotAuthorizedException;
import com.phoenix.authservice.exception.enums.AuthError;
import com.phoenix.authservice.model.AuthToken;
import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.model.ValidateTokenResponse;
import com.phoenix.authservice.service.AuthenticationService;
import com.phoenix.authservice.transform.ValidateTokenResponseBuilder;
import com.phoenix.authservice.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public AuthToken authenticate(LoginUser loginUser) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            String tokenString = tokenUtils.generateToken(authentication);
            AuthToken authToken = new AuthToken();
            authToken.setToken(tokenString);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authToken;
        } catch (BadCredentialsException exception) {
            throw new AuthServiceNotAuthorizedException(exception.getMessage(), exception.getCause(), AuthError.BAD_CREDENTIALS);
        } catch (AuthenticationException exception) {
            throw new AuthServiceNotAuthorizedException(exception.getMessage(), exception.getCause(), AuthError.NOT_AUTHORIZED_ERROR);
        } catch (Exception exception) {
            throw new AuthServiceInternalException(exception.getMessage(), exception.getCause(), AuthError.SERVER_ERROR);
        }
    }

    @Override
    public ValidateTokenResponse authenticate() {
        try {
            if (Objects.nonNull(SecurityContextHolder.getContext()) && Objects.nonNull(SecurityContextHolder.getContext().getAuthentication())) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (Objects.nonNull(authentication) && Objects.nonNull(authentication.getPrincipal())) {
                    return getValidateTokenResponse(authentication);
                }
            }
            throw new AuthServiceNotAuthorizedException("Full Authentication is Required.", AuthError.NOT_AUTHORIZED_ERROR);
        } catch (AuthServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthServiceInternalException(e.getMessage(), AuthError.SERVER_ERROR);
        }
    }

    @NotNull
    private static ValidateTokenResponse getValidateTokenResponse(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return ValidateTokenResponseBuilder.build(authentication.getAuthorities(), authentication.isAuthenticated(), user);
    }
}
