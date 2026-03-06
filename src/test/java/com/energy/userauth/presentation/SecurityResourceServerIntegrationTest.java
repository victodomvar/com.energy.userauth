package com.energy.userauth.presentation;

import com.energy.userauth.application.port.in.IdentityLinkUseCase;
import com.energy.userauth.application.port.in.UserUseCase;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@MockitoBean(types = JwtDecoder.class)
@MockitoBean(types = IdentityLinkUseCase.class)
class SecurityResourceServerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserUseCase userUseCase;

    @Test
    void getUsersWithoutTokenShouldReturn401() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }

    @Test
    void getUsersWithMalformedBearerTokenShouldReturn401() throws Exception {
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer invalid token"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void getUsersWithValidJwtAuthenticationShouldReturn200() throws Exception {
        User user = new User(
                1L,
                "john",
                "john@example.com",
                UserStatus.ACTIVE,
                Instant.parse("2026-03-06T12:00:00Z"),
                Instant.parse("2026-03-06T12:00:00Z")
        );
        when(userUseCase.getUser(null)).thenReturn(List.of(user));

        mockMvc.perform(get("/users").with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].userName").value("john"));
    }

    @Test
    void loginEndpointWithoutTokenShouldReturn401() throws Exception {
        mockMvc.perform(post("/users/login"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401));
    }

    @Test
    void loginEndpointWithJwtShouldReturn404() throws Exception {
        mockMvc.perform(post("/users/login").with(jwt()))
                .andExpect(status().isNotFound());
    }

    @Test
    void refreshTokenEndpointWithJwtShouldReturn404() throws Exception {
        mockMvc.perform(post("/users/refresh-token").with(jwt()))
                .andExpect(status().isNotFound());
    }
}
