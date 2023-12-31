package com.phoenix.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document("users")
public class User {

    @Id
    private String id;
    private String username;

    @JsonIgnore
    private String password;
    private String email;
    private String phone;
    private String name;
    private String businessTitle;
    private Set<String> roles;
}
