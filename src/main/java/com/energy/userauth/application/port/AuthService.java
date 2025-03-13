package com.energy.userauth.application.port;

import com.energy.userauth.openapi.model.AuthResponse;
import com.energy.userauth.openapi.model.LoginRequest;
import com.energy.userauth.openapi.model.RefreshTokenRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
