package com.energy.userauth.application.port;

import com.energy.userauth.openapi.model.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(Long id, UserDto userDto);
    List<UserDto> getUser (Long id);
}
