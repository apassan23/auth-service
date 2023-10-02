package com.phoenix.authservice.service;

import com.phoenix.authservice.model.AuthToken;
import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.model.ValidateTokenResponse;

public interface AuthenticationService {
    AuthToken authenticate(LoginUser loginUser);

    ValidateTokenResponse authenticate();
}
