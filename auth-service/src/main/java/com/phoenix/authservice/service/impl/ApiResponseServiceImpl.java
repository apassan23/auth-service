package com.phoenix.authservice.service.impl;

import com.phoenix.authservice.entity.Role;
import com.phoenix.authservice.entity.User;
import com.phoenix.authservice.exception.AuthServiceBadRequestException;
import com.phoenix.authservice.exception.enums.AuthError;
import com.phoenix.authservice.model.AuthToken;
import com.phoenix.authservice.model.AuthTokenResponse;
import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.service.ApiResponseService;
import com.phoenix.authservice.service.UserDataService;
import com.phoenix.authservice.transform.UserEntityBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ApiResponseServiceImpl implements ApiResponseService {
    private final UserDataService userDataService;

    public ApiResponseServiceImpl(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Override
    public AuthTokenResponse buildAuthTokenResponse(LoginUser loginUser, AuthToken authToken) {
        Optional<User> userOptional = userDataService.findUserByUsername(loginUser.getUsername());
        if (userOptional.isEmpty()) {
            throw new AuthServiceBadRequestException(String.format("No User found with username %s", loginUser.getUsername()), AuthError.NO_USER_FOUND);
        }
        User user = userOptional.get();
        Set<Role> roleSet = user.getRoles().stream().map(roleId -> {
            Optional<Role> roleOptional = userDataService.findRoleById(roleId);
            return roleOptional.orElseGet(() -> Role.builder().build());
        }).collect(Collectors.toUnmodifiableSet());
        AuthTokenResponse authTokenResponse = new AuthTokenResponse();
        authTokenResponse.setUser(UserEntityBuilder.build(user, roleSet));
        authTokenResponse.setAccessToken(authToken.getToken());
        return authTokenResponse;
    }
}
