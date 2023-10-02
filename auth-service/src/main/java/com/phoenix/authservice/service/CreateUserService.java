package com.phoenix.authservice.service;

import com.phoenix.authservice.model.UserDto;
import com.phoenix.authservice.model.UserResponseDto;

public interface CreateUserService {
    UserResponseDto given(UserDto userDto);
}
