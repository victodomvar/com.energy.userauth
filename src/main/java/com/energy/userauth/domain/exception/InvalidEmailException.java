package com.energy.userauth.domain.exception;

public class InvalidEmailException extends UserDomainException {
    public InvalidEmailException(String message) {
        super(message);
    }
}