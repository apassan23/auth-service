package com.phoenix.authservice.transform;

import com.phoenix.authservice.entity.Role;
import com.phoenix.authservice.model.RoleDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleEntityBuilder {

    public static RoleDto buildFromEntity(Role role) {
        return RoleDto.builder()
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
