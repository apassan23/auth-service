package com.phoenix.authservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class APIConstants {
    public static final String USER_PATH = "/v1/users";
    public static final String CREATE_USER_PATH = "/create";
    public static final String LOGIN_USER_PATH = "/login";
    public static final String ROLE_PATH = "/v1/roles";
    public static final String GET_ROLE_BY_NAME_PATH = "/{name}";
    public static final String CREATE_ROLE_PATH = "/create";
    public static final String GET_ALL_ROLES_PATH = "/";
    public static final String TOKEN_PATH = "/v1/tokens";
    public static final String TOKEN_VALIDATE_PATH = "/validate";
    public static final String[] API_WHITELIST = {
            USER_PATH + CREATE_USER_PATH,
            USER_PATH + LOGIN_USER_PATH
    };
}
