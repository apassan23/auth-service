package com.phoenix.authservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserResponseDto {
    private String username;
    private String email;
    private String phone;
    private String name;
    private String businessTitle;
    private Set<RoleDto> roles;
}
