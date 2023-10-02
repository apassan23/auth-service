package com.phoenix.authservice.controller.impl;

import com.phoenix.authservice.constants.APIConstants;
import com.phoenix.authservice.controller.RoleManagementAPI;
import com.phoenix.authservice.model.RoleDto;
import com.phoenix.authservice.service.RoleManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = APIConstants.ROLE_PATH)
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleManagementController implements RoleManagementAPI {
    private final RoleManagementService roleManagementService;

    public RoleManagementController(RoleManagementService roleManagementService) {
        this.roleManagementService = roleManagementService;
    }

    @Override
    @PostMapping(path = APIConstants.CREATE_ROLE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        RoleDto createdRole = roleManagementService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @Override
    @GetMapping(path = APIConstants.GET_ALL_ROLES_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = roleManagementService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @Override
    @GetMapping(path = APIConstants.GET_ROLE_BY_NAME_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoleDto> getRoleByName(@PathVariable String name) {
        RoleDto role = roleManagementService.getRoleByName(name);
        return ResponseEntity.ok(role);
    }
}
