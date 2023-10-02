package com.phoenix.authservice.service;

import com.phoenix.authservice.model.RoleDto;

import java.util.List;

public interface RoleManagementService {
    RoleDto createRole(RoleDto roleDto);

    List<RoleDto> getAllRoles();

    RoleDto getRoleByName(String name);
}
