package com.energy.userauth.application.service;

import com.energy.userauth.application.mapper.UserMapper;
import com.energy.userauth.application.port.UserRepository;
import com.energy.userauth.domain.exception.UserAlreadyExistsException;
import com.energy.userauth.domain.exception.UserNotFoundException;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.service.UserDomainService;
import com.energy.userauth.openapi.model.UserDto;
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
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserDto apiUserDto;
    private User domainUser;

    @BeforeEach
    void setUp() {
        apiUserDto = new UserDto().id(1L).userName("john").email("john@example.com").password("secret");
        domainUser = new User(1L, "john", "john@example.com", "secret");
    }

    @Test
    void createUser_withValidData_savesAndReturnsDto() {
        UserDto expectedResponse = new UserDto().id(2L).userName("john").email("john@example.com");
        User savedUser = new User(2L, "john", "john@example.com", "secret");

        when(userMapper.apiDtoToDomain(apiUserDto)).thenReturn(domainUser);
        when(userRepository.findByEmail(domainUser.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(domainUser)).thenReturn(savedUser);
        when(userMapper.domainToUserApiDto(savedUser)).thenReturn(expectedResponse);

        UserDto result = userService.createUser(apiUserDto);

        verify(userDomainService).validateUserCreation(domainUser);
        verify(userRepository).findByEmail("john@example.com");
        verify(userRepository).save(domainUser);
        assertEquals(expectedResponse, result);
    }

    @Test
    void createUser_whenEmailAlreadyExists_throwsException() {
        when(userMapper.apiDtoToDomain(apiUserDto)).thenReturn(domainUser);
        when(userRepository.findByEmail(domainUser.getEmail())).thenReturn(Optional.of(domainUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(apiUserDto));

        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_whenUserExists_updatesAndReturnsDto() {
        User savedUser = new User(1L, "john", "john@example.com", "updated");
        UserDto expectedResponse = new UserDto().id(1L).userName("john").email("john@example.com");

        when(userRepository.findById(apiUserDto.getId())).thenReturn(Optional.of(savedUser));
        when(userRepository.save(savedUser)).thenReturn(savedUser);
        when(userMapper.domainToUserApiDto(savedUser)).thenReturn(expectedResponse);

        UserDto result = userService.updateUser(apiUserDto.getId(), apiUserDto);

        verify(userRepository).findById(apiUserDto.getId());
        verify(userRepository).save(savedUser);
        assertEquals(expectedResponse, result);
    }

    @Test
    void updateUser_whenUserDoesNotExist_throwsException() {
        when(userRepository.findById(apiUserDto.getId())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(apiUserDto.getId(), apiUserDto));
    }

    @Test
    void getUser_withId_returnsSingleUser() {
        UserDto expectedResponse = new UserDto().id(1L).userName("john").email("john@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(domainUser));
        when(userMapper.domainToUserApiDto(domainUser)).thenReturn(expectedResponse);

        List<UserDto> result = userService.getUser(1L);

        assertThat(result).hasSize(1).containsExactly(expectedResponse);
        verify(userRepository, never()).findAll();
    }

    @Test
    void getUser_withoutId_returnsAllUsers() {
        List<User> users = Collections.singletonList(domainUser);
        List<UserDto> expected = Collections.singletonList(new UserDto().id(1L).userName("john"));

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.domainToApiDtoList(users)).thenReturn(expected);

        List<UserDto> result = userService.getUser(null);

        assertEquals(expected, result);
        verify(userRepository).findAll();
    }
}
