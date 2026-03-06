package com.energy.userauth.application.usecase;

import com.energy.userauth.application.port.in.IdentityLinkUseCase;
import com.energy.userauth.application.port.out.IdentityLinkRepositoryPort;
import com.energy.userauth.application.port.out.UserRepositoryPort;
import com.energy.userauth.domain.exception.IdentityLinkAlreadyExistsException;
import com.energy.userauth.domain.exception.UserNotFoundException;
import com.energy.userauth.domain.model.IdentityLink;
import com.energy.userauth.domain.service.IdentityLinkDomainService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentityLinkUseCaseImpl implements IdentityLinkUseCase {

    private final IdentityLinkRepositoryPort identityLinkRepository;
    private final UserRepositoryPort userRepository;
    private final IdentityLinkDomainService identityLinkDomainService;

    public IdentityLinkUseCaseImpl(IdentityLinkRepositoryPort identityLinkRepository,
                                   UserRepositoryPort userRepository,
                                   IdentityLinkDomainService identityLinkDomainService) {
        this.identityLinkRepository = identityLinkRepository;
        this.userRepository = userRepository;
        this.identityLinkDomainService = identityLinkDomainService;
    }

    @Override
    public IdentityLink createIdentityLink(Long userId, IdentityLink identityLink) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        identityLink.setUserId(userId);
        identityLinkDomainService.validateIdentityLink(identityLink);

        identityLinkRepository.findByIssuerAndSubject(identityLink.getIssuer(), identityLink.getSubject())
                .ifPresent(link -> {
                    throw new IdentityLinkAlreadyExistsException(
                            "Identity link already exists for issuer " + identityLink.getIssuer()
                                    + " and subject " + identityLink.getSubject());
                });

        return identityLinkRepository.save(identityLink);
    }

    @Override
    public List<IdentityLink> getIdentityLinksByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        return identityLinkRepository.findByUserId(userId);
    }
}
