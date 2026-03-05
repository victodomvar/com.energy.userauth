package com.energy.userauth.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "superSecretKey1234567890abcd123456");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 3_600_000L);
        userDetails = User.withUsername("admin").password("password").authorities("ROLE_USER").build();
    }

    @Test
    void generateToken_andExtractUsername() {
        String token = jwtUtil.generateToken(userDetails);

        assertThat(token).isNotBlank();
        assertThat(jwtUtil.extractUsername(token)).isEqualTo("admin");
    }

    @Test
    void isTokenValid_returnsTrueForMatchingUser() {
        String token = jwtUtil.generateToken(userDetails);

        assertTrue(jwtUtil.isTokenValid(token, userDetails));
    }

    @Test
    void validateToken_returnsFalseForInvalidToken() {
        assertFalse(jwtUtil.validateToken("invalid"));
    }
}
