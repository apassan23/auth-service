package com.phoenix.authservice.controller;

import com.phoenix.authservice.model.AuthTokenResponse;
import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.model.UserDto;
import com.phoenix.authservice.model.UserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserManagementAPI {
    ResponseEntity<UserResponseDto> createUser(UserDto user);

    ResponseEntity<AuthTokenResponse> generateToken(LoginUser loginUser);
}
