package com.phoenix.authservice.service;

import com.phoenix.authservice.entity.Role;
import com.phoenix.authservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDataService {

    Optional<User> findUserById(String id);

    Optional<User> findUserByUsername(String username);

    Optional<Role> findRoleById(String id);

    User save(User user);

    Role save(Role role);

    Optional<Role> findRoleByName(String name);

    List<Role> findAllRoles();
}
