package com.energy.userauth.application.service;

import com.energy.userauth.infrastructure.security.JwtUtil;
import com.energy.userauth.openapi.model.AuthResponse;
import com.energy.userauth.openapi.model.LoginRequest;
import com.energy.userauth.openapi.model.RefreshTokenRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_returnsTokenAndExpiration() {
        LoginRequest request = new LoginRequest().userName("admin").password("password");
        UserDetails userDetails = User.withUsername("admin").password("password").authorities("ROLE_USER").build();

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwtToken");
        when(jwtUtil.getExpiration()).thenReturn(120_000L);

        AuthResponse response = authService.login(request);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        assertEquals("jwtToken", response.getToken());
        assertEquals(120, response.getExpiresIn());
    }

    @Test
    void refreshToken_withValidToken_createsNewAccessToken() {
        RefreshTokenRequest request = new RefreshTokenRequest().refreshToken("refreshToken");

        when(jwtUtil.validateToken("refreshToken")).thenReturn(true);
        when(jwtUtil.extractUsername("refreshToken")).thenReturn("admin");
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("newAccessToken");
        when(jwtUtil.getExpiration()).thenReturn(60_000L);

        AuthResponse response = authService.refreshToken(request);

        ArgumentCaptor<UserDetails> captor = ArgumentCaptor.forClass(UserDetails.class);
        verify(jwtUtil).generateToken(captor.capture());
        assertThat(captor.getValue().getUsername()).isEqualTo("admin");
        assertEquals("newAccessToken", response.getToken());
        assertEquals(60, response.getExpiresIn());
    }

    @Test
    void refreshToken_withInvalidToken_throwsException() {
        RefreshTokenRequest request = new RefreshTokenRequest().refreshToken("invalid");

        when(jwtUtil.validateToken("invalid")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.refreshToken(request));
    }
}
