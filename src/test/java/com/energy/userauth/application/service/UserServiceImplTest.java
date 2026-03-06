package com.energy.userauth.application.service;

import com.energy.userauth.application.port.out.UserRepositoryPort;
import com.energy.userauth.application.usecase.UserUseCaseImpl;
import com.energy.userauth.domain.exception.UserAlreadyExistsException;
import com.energy.userauth.domain.exception.UserNotFoundException;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.model.UserStatus;
import com.energy.userauth.domain.service.UserDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDomainService userDomainService;
    @Mock
    private UserRepositoryPort userRepository;

    @InjectMocks
    private UserUseCaseImpl userService;

    private User domainUser;

    @BeforeEach
    void setUp() {
        domainUser = new User(1L, "john", "john@example.com", UserStatus.ACTIVE, null, null);
    }

    @Test
    void createUser_withValidData_savesAndReturnsUser() {
        User savedUser = new User(2L, "john", "john@example.com", UserStatus.ACTIVE, null, null);

        when(userRepository.findByEmail(domainUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(domainUser)).thenReturn(savedUser);

        User result = userService.createUser(domainUser);

        verify(userDomainService).validateUserCreation(domainUser);
        verify(userRepository).findByEmail("john@example.com");
        verify(userRepository).save(domainUser);
        assertEquals(savedUser, result);
    }

    @Test
    void createUser_whenEmailAlreadyExists_throwsException() {
        when(userRepository.findByEmail(domainUser.getEmail())).thenReturn(Optional.of(domainUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(domainUser));

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_whenUserExists_updatesAndReturnsUser() {
        User savedUser = new User(1L, "john", "john@example.com", UserStatus.ACTIVE, null, null);
        User updateRequest = new User(999L, "john", "john@example.com", UserStatus.SUSPENDED, null, null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(savedUser));
        when(userRepository.save(savedUser)).thenReturn(savedUser);

        User result = userService.updateUser(1L, updateRequest);

        verify(userRepository).findById(1L);
        verify(userRepository).save(savedUser);
        assertEquals(UserStatus.SUSPENDED, savedUser.getStatus());
        assertEquals(savedUser, result);
    }

    @Test
    void updateUser_whenUserDoesNotExist_throwsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, domainUser));
    }

    @Test
    void getUser_withId_returnsSingleUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(domainUser));

        List<User> result = userService.getUser(1L);

        assertThat(result).hasSize(1).containsExactly(domainUser);
        verify(userRepository, never()).findAll();
    }

    @Test
    void getUser_withoutId_returnsAllUsers() {
        List<User> users = Collections.singletonList(domainUser);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getUser(null);

        assertEquals(users, result);
        verify(userRepository).findAll();
    }
}
