package com.energy.userauth.application.port;

import com.energy.userauth.openapi.model.AuthResponse;
import com.energy.userauth.openapi.model.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest loginRequest);
}
