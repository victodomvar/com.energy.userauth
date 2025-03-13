package com.energy.userauth.application.service;

import com.energy.userauth.application.mapper.UserMapper;
import com.energy.userauth.application.port.UserRepository;
import com.energy.userauth.application.port.UserService;
import com.energy.userauth.domain.exception.UserAlreadyExistsException;
import com.energy.userauth.domain.exception.UserNotFoundException;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.service.UserDomainService;
import com.energy.userauth.openapi.model.UserDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDomainService userDomainService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDomainService userDomainService,
                           UserRepository userRepository,
                           UserMapper userMapper) {
        this.userDomainService = userDomainService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.apiDtoToDomain(userDto);
        userDomainService.validateUserCreation(user);
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " already exists.");
                });
        return userMapper.domainToUserApiDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User savedUser = userRepository.findById(userDto.getId())
                .map(userRepository::save)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userDto.getId()));
        return userMapper.domainToUserApiDto(savedUser);

    }

    @Override
    public List<UserDto> getUser(Long id) {
        if (id != null) {
            User foundUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
            return Collections.singletonList(userMapper.domainToUserApiDto(foundUser));
        } else {
            List<User> users = userRepository.findAll();
            return userMapper.domainToApiDtoList(users);
        }
    }


}
