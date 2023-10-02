package com.phoenix.authservice.service.impl;

import com.phoenix.authservice.exception.AuthServiceBadRequestException;
import com.phoenix.authservice.exception.enums.AuthError;
import com.phoenix.authservice.model.LoginUser;
import com.phoenix.authservice.model.RetrieveRoleRequest;
import com.phoenix.authservice.model.RoleDto;
import com.phoenix.authservice.model.UserDto;
import com.phoenix.authservice.service.ValidationService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ValidationServiceImpl implements ValidationService {
    @Override
    public void validateUser(UserDto user) {
        if (Objects.nonNull(user) &&
                Objects.nonNull(user.getBusinessTitle()) &&
                Objects.nonNull(user.getEmail()) &&
                Objects.nonNull(user.getName()) &&
                Objects.nonNull(user.getPhone()) &&
                Objects.nonNull(user.getPassword()) &&
                Objects.nonNull(user.getUsername())
        ) {
            return;
        }

        throw new AuthServiceBadRequestException("Request was malformed", AuthError.BAD_REQUEST);
    }

    @Override
    public void validateUser(LoginUser loginUser) {
        if (Objects.nonNull(loginUser) &&
                Objects.nonNull(loginUser.getUsername()) &&
                Objects.nonNull(loginUser.getPassword())
        ) {
            return;
        }

        throw new AuthServiceBadRequestException("Request was malformed", AuthError.BAD_REQUEST);
    }

    @Override
    public void validate(RoleDto roleDto) {
        if (Objects.nonNull(roleDto) &&
                Objects.nonNull(roleDto.getName()) &&
                Objects.nonNull(roleDto.getDescription())
        ) {
            return;
        }
        throw new AuthServiceBadRequestException("Request was malformed", AuthError.BAD_REQUEST);
    }

    @Override
    public void validate(RetrieveRoleRequest retrieveRoleRequest) {
        if (Objects.nonNull(retrieveRoleRequest) && Objects.nonNull(retrieveRoleRequest.getName())) {
            return;
        }
        throw new AuthServiceBadRequestException("Request was malformed", AuthError.BAD_REQUEST);
    }
}
