package com.phoenix.authservice.service;

import com.phoenix.authservice.model.ValidateTokenResponse;

public interface TokenManagementService {
    ValidateTokenResponse validate();
}
