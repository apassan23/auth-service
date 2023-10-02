package com.phoenix.authservice.controller;

import com.phoenix.authservice.model.RoleDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleManagementAPI {
    ResponseEntity<RoleDto> createRole(RoleDto roleDto);

    ResponseEntity<List<RoleDto>> getAllRoles();

    ResponseEntity<RoleDto> getRoleByName(String name);
}
