package com.phoenix.authservice.controller;

import com.phoenix.authservice.model.ValidateTokenResponse;
import org.springframework.http.ResponseEntity;

public interface TokenManagementAPI {

    ResponseEntity<ValidateTokenResponse> validateToken();
}
