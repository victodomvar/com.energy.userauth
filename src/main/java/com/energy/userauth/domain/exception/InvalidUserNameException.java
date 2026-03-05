package com.energy.userauth.domain.exception;

public class InvalidUserNameException extends UserDomainException {
    public InvalidUserNameException(String message) {
        super(message);
    }
}