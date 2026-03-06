package com.energy.userauth.domain.service;

import com.energy.userauth.domain.exception.InvalidEmailException;
import com.energy.userauth.domain.exception.InvalidUserNameException;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.model.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserDomainServiceTest {

    private UserDomainService userDomainService;

    @BeforeEach
    void setUp() {
        userDomainService = new UserDomainService();
    }

    @Test
    void validateUserCreation_withValidUser_doesNotThrowException() {
        User validUser = new User(1L, "john_doe", "john@example.com", UserStatus.ACTIVE, null, null);

        assertDoesNotThrow(() -> userDomainService.validateUserCreation(validUser));
    }

    @Test
    void validateUserCreation_whenUserNameIsBlank_throwsInvalidUserNameException() {
        User invalidUser = new User(1L, "   ", "john@example.com", UserStatus.ACTIVE, null, null);

        assertThrows(InvalidUserNameException.class, () -> userDomainService.validateUserCreation(invalidUser));
    }

    @Test
    void validateUserCreation_whenEmailIsInvalid_throwsInvalidEmailException() {
        User invalidUser = new User(1L, "john_doe", "invalidEmail", UserStatus.ACTIVE, null, null);

        assertThrows(InvalidEmailException.class, () -> userDomainService.validateUserCreation(invalidUser));
    }
}
