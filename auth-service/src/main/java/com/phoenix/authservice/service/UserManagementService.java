package com.phoenix.authservice.service;

import com.phoenix.authservice.model.AuthTokenResponse;
import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.model.UserDto;
import com.phoenix.authservice.model.UserResponseDto;

public interface UserManagementService {
    UserResponseDto createUser(UserDto user);

    AuthTokenResponse generateToken(LoginUser loginUser);
}
