package com.energy.userauth.infrastructure.persistence;

import com.energy.userauth.application.port.out.IdentityLinkRepositoryPort;
import com.energy.userauth.domain.exception.UserNotFoundException;
import com.energy.userauth.domain.model.IdentityLink;
import com.energy.userauth.infrastructure.persistence.entity.UserEntity;
import com.energy.userauth.infrastructure.persistence.mapper.IdentityLinkPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IdentityLinkRepositoryAdapter implements IdentityLinkRepositoryPort {

    private final JpaIdentityLinkRepository jpaIdentityLinkRepository;
    private final JpaUserRepository jpaUserRepository;
    private final IdentityLinkPersistenceMapper mapper;

    public IdentityLinkRepositoryAdapter(JpaIdentityLinkRepository jpaIdentityLinkRepository,
                                         JpaUserRepository jpaUserRepository,
                                         IdentityLinkPersistenceMapper mapper) {
        this.jpaIdentityLinkRepository = jpaIdentityLinkRepository;
        this.jpaUserRepository = jpaUserRepository;
        this.mapper = mapper;
    }

    @Override
    public IdentityLink save(IdentityLink identityLink) {
        UserEntity userEntity = jpaUserRepository.findById(identityLink.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + identityLink.getUserId()));
        return mapper.entityToDomain(
                jpaIdentityLinkRepository.save(mapper.domainToEntity(identityLink, userEntity))
        );
    }

    @Override
    public List<IdentityLink> findByUserId(Long userId) {
        return mapper.entityListToDomainList(jpaIdentityLinkRepository.findByUser_Id(userId));
    }

    @Override
    public Optional<IdentityLink> findByIssuerAndSubject(String issuer, String subject) {
        return jpaIdentityLinkRepository.findByIssuerAndSubject(issuer, subject).map(mapper::entityToDomain);
    }
}
