package com.phoenix.authservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String name;
    private String businessTitle;
}
