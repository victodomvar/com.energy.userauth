package com.energy.userauth.application.port.out;

import com.energy.userauth.domain.model.IdentityLink;

import java.util.List;
import java.util.Optional;

public interface IdentityLinkRepositoryPort {
    IdentityLink save(IdentityLink identityLink);
    List<IdentityLink> findByUserId(Long userId);
    Optional<IdentityLink> findByIssuerAndSubject(String issuer, String subject);
}
