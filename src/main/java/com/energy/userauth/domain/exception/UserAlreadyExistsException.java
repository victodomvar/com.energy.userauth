package com.energy.userauth.domain.exception;

public class UserAlreadyExistsException extends UserDomainException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
