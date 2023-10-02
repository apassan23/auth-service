package com.phoenix.authservice.service;

import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.model.RetrieveRoleRequest;
import com.phoenix.authservice.model.RoleDto;
import com.phoenix.authservice.model.UserDto;

public interface ValidationService {
    void validateUser(UserDto user);

    void validateUser(LoginUser loginUser);

    void validate(RoleDto roleDto);

    void validate(RetrieveRoleRequest retrieveRoleRequest);
}
