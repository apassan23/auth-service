package com.phoenix.authservice.service.impl;

import com.phoenix.authservice.entity.Role;
import com.phoenix.authservice.entity.User;
import com.phoenix.authservice.repository.UserRepository;
import com.phoenix.authservice.repository.UserRoleRepository;
import com.phoenix.authservice.service.UserDataService;
import com.phoenix.authservice.transform.UserEntityBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDataService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
        User user = userOptional.get();
        Set<Role> roles = user.getRoles().stream()
                .map(roleId -> {
                    Optional<Role> roleOptional = findRoleById(roleId);
                    return roleOptional.orElse(Role.builder().build());
                }).collect(Collectors.toUnmodifiableSet());
        return UserEntityBuilder.buildFrom(user.getUsername(), user.getPassword(), roles);
    }


    @Override
    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Role> findRoleById(String id) {
        return userRoleRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role save(Role role) {
        return userRoleRepository.save(role);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        return userRoleRepository.findByName(name);
    }

    @Override
    public List<Role> findAllRoles() {
        return userRoleRepository.findAll();
    }

}
