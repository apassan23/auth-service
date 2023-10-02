package com.phoenix.authservice.service.impl;

import com.phoenix.authservice.entity.Role;
import com.phoenix.authservice.exception.AuthServiceBadRequestException;
import com.phoenix.authservice.exception.enums.AuthError;
import com.phoenix.authservice.model.RetrieveRoleRequest;
import com.phoenix.authservice.model.RoleDto;
import com.phoenix.authservice.service.RoleManagementService;
import com.phoenix.authservice.service.UserDataService;
import com.phoenix.authservice.service.ValidationService;
import com.phoenix.authservice.transform.RetrieveRoleRequestBuilder;
import com.phoenix.authservice.transform.RoleEntityBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleManagementServiceImpl implements RoleManagementService {
    private final ValidationService validationService;
    private final UserDataService userDataService;

    public RoleManagementServiceImpl(ValidationService validationService, UserDataService userDataService) {
        this.validationService = validationService;
        this.userDataService = userDataService;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        validationService.validate(roleDto);
        Optional<Role> findByNameOptional = userDataService.findRoleByName(roleDto.getName());
        if (findByNameOptional.isPresent()) {
            throw new AuthServiceBadRequestException(String.format("ROLE %s already exists.", roleDto.getName()), AuthError.ROLE_ALREADY_EXISTS);
        }
        Role role = new Role();
        role.setName(roleDto.getName().toUpperCase());
        role.setDescription(roleDto.getDescription());
        Role savedRole = userDataService.save(role);
        return RoleEntityBuilder.buildFromEntity(savedRole);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roleEntities = userDataService.findAllRoles();
        return roleEntities.stream().map(RoleEntityBuilder::buildFromEntity).toList();
    }

    @Override
    public RoleDto getRoleByName(String name) {
        RetrieveRoleRequest retrieveRoleRequest = RetrieveRoleRequestBuilder.build(name);
        validationService.validate(retrieveRoleRequest);
        Optional<Role> findByNameOptional = userDataService.findRoleByName(retrieveRoleRequest.getName());
        if (findByNameOptional.isEmpty()) {
            throw new AuthServiceBadRequestException(String.format("ROLE %s does not exist.", retrieveRoleRequest.getName()), AuthError.NO_ROLE_FOUND);
        }
        return RoleEntityBuilder.buildFromEntity(findByNameOptional.get());
    }
}
