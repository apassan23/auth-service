package com.phoenix.authservice.service.impl;

import com.phoenix.authservice.model.AuthToken;
import com.phoenix.authservice.model.AuthTokenResponse;
import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.model.UserDto;
import com.phoenix.authservice.model.UserResponseDto;
import com.phoenix.authservice.service.ApiResponseService;
import com.phoenix.authservice.service.AuthenticationService;
import com.phoenix.authservice.service.CreateUserService;
import com.phoenix.authservice.service.UserManagementService;
import com.phoenix.authservice.service.ValidationService;
import org.springframework.stereotype.Service;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    private final AuthenticationService authenticationService;
    private final ValidationService validationService;
    private final CreateUserService createUserService;
    private final ApiResponseService apiResponseService;

    public UserManagementServiceImpl(AuthenticationService authenticationService, ValidationService validationService, CreateUserService createUserService, ApiResponseService apiResponseService) {
        this.authenticationService = authenticationService;
        this.validationService = validationService;
        this.createUserService = createUserService;
        this.apiResponseService = apiResponseService;
    }

    @Override
    public UserResponseDto createUser(UserDto userDto) {
        validationService.validateUser(userDto);
        return createUserService.given(userDto);
    }

    @Override
    public AuthTokenResponse generateToken(LoginUser loginUser) {
        validationService.validateUser(loginUser);
        AuthToken authToken = authenticationService.authenticate(loginUser);
        return apiResponseService.buildAuthTokenResponse(loginUser, authToken);
    }
}
