package com.energy.userauth.application.port.in;

import com.energy.userauth.domain.model.User;

import java.util.List;

public interface UserUseCase {
    User createUser(User user);
    User updateUser(Long id, User user);
    List<User> getUser(Long id);
}
