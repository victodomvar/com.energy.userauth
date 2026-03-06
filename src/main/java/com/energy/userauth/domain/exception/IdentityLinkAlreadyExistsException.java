package com.energy.userauth.domain.exception;

public class IdentityLinkAlreadyExistsException extends UserDomainException {
    public IdentityLinkAlreadyExistsException(String message) {
        super(message);
    }
}
