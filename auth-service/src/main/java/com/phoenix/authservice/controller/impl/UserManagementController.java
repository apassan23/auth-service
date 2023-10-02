package com.phoenix.authservice.controller.impl;

import com.phoenix.authservice.constants.APIConstants;
import com.phoenix.authservice.controller.UserManagementAPI;
import com.phoenix.authservice.model.AuthTokenResponse;
import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.model.UserDto;
import com.phoenix.authservice.model.UserResponseDto;
import com.phoenix.authservice.service.UserManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = APIConstants.USER_PATH)
public class UserManagementController implements UserManagementAPI {

    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @Override
    @PostMapping(path = APIConstants.CREATE_USER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserDto user) {
        UserResponseDto userResponse = userManagementService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @Override
    @PostMapping(path = APIConstants.LOGIN_USER_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthTokenResponse> generateToken(@RequestBody LoginUser loginUser) {
        AuthTokenResponse authTokenResponse = userManagementService.generateToken(loginUser);
        return ResponseEntity.ok().body(authTokenResponse);
    }
}
