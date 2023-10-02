package com.phoenix.authservice.service.impl;

import com.phoenix.authservice.model.ValidateTokenResponse;
import com.phoenix.authservice.service.AuthenticationService;
import com.phoenix.authservice.service.TokenManagementService;
import org.springframework.stereotype.Service;

@Service
public class TokenManagementServiceImpl implements TokenManagementService {
    private final AuthenticationService authenticationService;

    public TokenManagementServiceImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public ValidateTokenResponse validate() {
        return authenticationService.authenticate();
    }
}
