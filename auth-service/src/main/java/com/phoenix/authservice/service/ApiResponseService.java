package com.phoenix.authservice.service;

import com.phoenix.authservice.model.AuthToken;
import com.phoenix.authservice.model.AuthTokenResponse;
import com.phoenix.authservice.model.LoginUser;

public interface ApiResponseService {

    AuthTokenResponse buildAuthTokenResponse(LoginUser loginUser, AuthToken authToken);
}
