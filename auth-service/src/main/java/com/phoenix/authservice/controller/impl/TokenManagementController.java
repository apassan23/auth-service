package com.phoenix.authservice.controller.impl;

import com.phoenix.authservice.constants.APIConstants;
import com.phoenix.authservice.controller.TokenManagementAPI;
import com.phoenix.authservice.model.ValidateTokenResponse;
import com.phoenix.authservice.service.TokenManagementService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = APIConstants.TOKEN_PATH)
public class TokenManagementController implements TokenManagementAPI {
    private final TokenManagementService tokenManagementService;

    public TokenManagementController(TokenManagementService tokenManagementService) {
        this.tokenManagementService = tokenManagementService;
    }

    @Override
    @PostMapping(path = APIConstants.TOKEN_VALIDATE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidateTokenResponse> validateToken() {
        ValidateTokenResponse validateTokenResponse = tokenManagementService.validate();
        return ResponseEntity.ok(validateTokenResponse);
    }
}
