package com.phoenix.authservice.transform;

import com.phoenix.authservice.entity.Role;
import com.phoenix.authservice.model.RoleDto;
import com.phoenix.authservice.model.UserDto;
import com.phoenix.authservice.model.UserResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserEntityBuilder {

    public static User buildFrom(String username, String password, Set<Role> roles) {
        return new User(username, password, getAuthorities(roles));
    }

    public static com.phoenix.authservice.entity.User buildFromDTO(UserDto userDto) {
        return com.phoenix.authservice.entity.User.builder()
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .businessTitle(userDto.getBusinessTitle())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }

    public static UserResponseDto build(com.phoenix.authservice.entity.User user, Set<Role> roles) {
        return UserResponseDto.builder()
                .phone(user.getPhone())
                .email(user.getEmail())
                .name(user.getName())
                .businessTitle(user.getBusinessTitle())
                .username(user.getUsername())
                .roles(getRoles(roles))
                .build();
    }

    private static Set<RoleDto> getRoles(Set<Role> roles) {
        return roles.stream().map(UserEntityBuilder::build).collect(Collectors.toUnmodifiableSet());
    }

    public static RoleDto build(Role role) {
        return RoleDto.builder()
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    private static Set<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(String.join("ROLE_%s", role.getName())));
        });
        return authorities;
    }
}
