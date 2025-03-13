package com.energy.userauth.application.service;

import com.energy.userauth.application.mapper.UserMapper;
import com.energy.userauth.application.port.UserRepository;
import com.energy.userauth.application.port.UserService;
import com.energy.userauth.domain.exception.UserAlreadyExistsException;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.service.UserDomainService;
import com.energy.userauth.openapi.model.UserDto;
import org.springframework.stereotype.Service;

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
        User user = userMapper.toDomain(userDto);
        userDomainService.validateUserCreation(userMapper.toDomain(userDto));
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new UserAlreadyExistsException("A user with email " + user.getEmail() + " already exists.");
                });
        User savedUser = userRepository.save(user);
        return userMapper.toUserApiDto(savedUser);
    }

}
