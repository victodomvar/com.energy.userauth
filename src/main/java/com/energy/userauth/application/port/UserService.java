package com.energy.userauth.application.port;

import com.energy.userauth.openapi.model.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
