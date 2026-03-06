package com.energy.userauth.application.port.in;

import com.energy.userauth.domain.model.IdentityLink;

import java.util.List;

public interface IdentityLinkUseCase {
    IdentityLink createIdentityLink(Long userId, IdentityLink identityLink);
    List<IdentityLink> getIdentityLinksByUserId(Long userId);
}
