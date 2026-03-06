package com.energy.userauth.domain.service;

import com.energy.userauth.domain.exception.InvalidEmailException;
import com.energy.userauth.domain.exception.InvalidUserNameException;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.model.UserStatus;

public class UserDomainService {

    public void validateUserCreation(User user) {
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            throw new InvalidUserNameException("El nombre de usuario no puede estar vacío.");
        }

        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            throw new InvalidEmailException("El email proporcionado no es válido.");
        }

        if (user.getStatus() == null) {
            user.setStatus(UserStatus.ACTIVE);
        }
    }
}
