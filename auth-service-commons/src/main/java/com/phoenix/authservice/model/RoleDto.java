package com.phoenix.authservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
    private String name;
    private String description;
}
