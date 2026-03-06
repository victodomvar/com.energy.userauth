package com.energy.userauth.domain.service;

import com.energy.userauth.domain.exception.UserDomainException;
import com.energy.userauth.domain.model.IdentityLink;

public class IdentityLinkDomainService {

    public void validateIdentityLink(IdentityLink identityLink) {
        if (identityLink.getIssuer() == null || identityLink.getIssuer().trim().isEmpty()) {
            throw new UserDomainException("Identity link issuer cannot be empty.");
        }

        if (identityLink.getSubject() == null || identityLink.getSubject().trim().isEmpty()) {
            throw new UserDomainException("Identity link subject cannot be empty.");
        }

        if (identityLink.getProvider() == null || identityLink.getProvider().trim().isEmpty()) {
            throw new UserDomainException("Identity link provider cannot be empty.");
        }
    }
}
