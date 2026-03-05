package com.energy.userauth.presentation.controller;


import com.energy.userauth.application.port.AuthService;
import com.energy.userauth.application.port.UserService;
import com.energy.userauth.openapi.api.UserApi;
import com.energy.userauth.openapi.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUser(Long id) {
        return ResponseEntity.ok( userService.getUser(id));
    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
            return ResponseEntity.ok( authService.login(loginRequest));
    }

    @Override
    public ResponseEntity<AuthResponse> refreshToken(RefreshTokenRequest refreshTokenRequest) {

        return ResponseEntity.ok( authService.refreshToken(refreshTokenRequest));

    }

    @Override
    public ResponseEntity<UserDto> updateUser(Long id, UserDto userDto) {
        UserDto updatedUser = userService.updateUser( id, userDto);
        return ResponseEntity.ok(updatedUser);

    }
}
