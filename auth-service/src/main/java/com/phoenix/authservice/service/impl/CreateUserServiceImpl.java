package com.phoenix.authservice.service.impl;

import com.phoenix.authservice.constants.UserConstants;
import com.phoenix.authservice.entity.Role;
import com.phoenix.authservice.entity.User;
import com.phoenix.authservice.exception.AuthServiceBadRequestException;
import com.phoenix.authservice.exception.AuthServiceException;
import com.phoenix.authservice.exception.AuthServiceInternalException;
import com.phoenix.authservice.exception.enums.AuthError;
import com.phoenix.authservice.model.UserDto;
import com.phoenix.authservice.model.UserResponseDto;
import com.phoenix.authservice.service.CreateUserService;
import com.phoenix.authservice.service.UserDataService;
import com.phoenix.authservice.transform.UserEntityBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreateUserServiceImpl implements CreateUserService {

    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;

    public CreateUserServiceImpl(UserDataService userDataService, PasswordEncoder passwordEncoder) {
        this.userDataService = userDataService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDto given(UserDto userDto) {
        User user = UserEntityBuilder.buildFromDTO(userDto);
        try {
            Optional<User> findByUsername = userDataService.findUserByUsername(user.getUsername());
            if (findByUsername.isPresent()) {
                throw new AuthServiceBadRequestException(String.format("User with username %s already exists.", user.getUsername()), AuthError.USER_ALREADY_EXISTS);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Optional<Role> roleOptional = userDataService.findRoleByName(UserConstants.USER_ROLE);
            if (roleOptional.isEmpty()) {
                throw new AuthServiceInternalException(String.format("ROLE %s does not exist.", UserConstants.USER_ROLE), AuthError.NO_ROLE_FOUND);
            }
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleOptional.get());
            if (Objects.requireNonNull(StringUtils.split(user.getEmail(), UserConstants.EMAIL_DELIMITER))[1].equalsIgnoreCase(UserConstants.ADMIN_DOMAIN)) {
                Optional<Role> adminRoleOptional = userDataService.findRoleByName(UserConstants.ADMIN_ROLE);
                if (adminRoleOptional.isEmpty()) {
                    throw new AuthServiceInternalException(String.format("ROLE %s does not exist.", UserConstants.ADMIN_ROLE), AuthError.NO_ROLE_FOUND);
                }
                roleSet.add(adminRoleOptional.get());
            }
            Set<String> roleIds = roleSet.stream().map(Role::getId).collect(Collectors.toUnmodifiableSet());
            user.setRoles(roleIds);
            userDataService.save(user);
            return UserEntityBuilder.build(user, roleSet);
        } catch (AuthServiceException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new AuthServiceInternalException(exception.getMessage(), exception, AuthError.SERVER_ERROR);
        }
    }
}
