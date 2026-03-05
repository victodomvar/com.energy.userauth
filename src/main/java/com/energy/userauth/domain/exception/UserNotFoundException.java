package com.energy.userauth.domain.exception;


public class UserNotFoundException extends UserDomainException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
