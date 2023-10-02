package com.phoenix.authservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConstants {

    public static final String USER_ROLE = "USER";
    public static final String ADMIN_ROLE = "ADMIN";
    public static final String EMAIL_DELIMITER = "@";
    public static final String ADMIN_DOMAIN = "admin";
}
