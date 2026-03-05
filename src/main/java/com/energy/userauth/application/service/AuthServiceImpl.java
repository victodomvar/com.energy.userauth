package com.energy.userauth.application.service;

import com.energy.userauth.application.port.AuthService;
import com.energy.userauth.infrastructure.security.JwtUtil;
import com.energy.userauth.openapi.model.AuthResponse;
import com.energy.userauth.openapi.model.LoginRequest;
import com.energy.userauth.openapi.model.RefreshTokenRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse()
                .token(token)
                .expiresIn((int)jwtUtil.getExpiration() / 1000);

    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, "", new ArrayList<>());

            String newAccessToken = jwtUtil.generateToken(userDetails);

            return new AuthResponse()
                    .token(newAccessToken)
                    .expiresIn((int) jwtUtil.getExpiration() / 1000);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}



