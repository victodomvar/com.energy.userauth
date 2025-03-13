package com.energy.userauth.presentation.controller;


import com.energy.userauth.application.port.AuthService;
import com.energy.userauth.application.port.UserService;
import com.energy.userauth.openapi.api.UserApi;
import com.energy.userauth.openapi.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public ResponseEntity<Void> changePassword(Integer id, ChangePasswordRequest changePasswordRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @Override
    public ResponseEntity<Void> forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UserDto> getUser(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
            return ResponseEntity.ok( authService.login(loginRequest));
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return null;
    }

    @Override
    public ResponseEntity<UserDto> updateUser(Integer id, UserDto userDto) {
        return null;
    }
}
