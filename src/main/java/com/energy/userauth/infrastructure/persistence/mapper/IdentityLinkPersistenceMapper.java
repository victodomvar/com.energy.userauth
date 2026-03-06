package com.energy.userauth.infrastructure.persistence.mapper;

import com.energy.userauth.domain.model.IdentityLink;
import com.energy.userauth.infrastructure.persistence.entity.IdentityLinkEntity;
import com.energy.userauth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IdentityLinkPersistenceMapper {

    public IdentityLinkEntity domainToEntity(IdentityLink identityLink, UserEntity userEntity) {
        if (identityLink == null) {
            return null;
        }

        IdentityLinkEntity entity = new IdentityLinkEntity();
        entity.setId(identityLink.getId());
        entity.setUser(userEntity);
        entity.setIssuer(identityLink.getIssuer());
        entity.setSubject(identityLink.getSubject());
        entity.setProvider(identityLink.getProvider());
        entity.setCreatedAt(identityLink.getCreatedAt());
        return entity;
    }

    public IdentityLink entityToDomain(IdentityLinkEntity entity) {
        if (entity == null) {
            return null;
        }

        return new IdentityLink(
                entity.getId(),
                entity.getUser().getId(),
                entity.getIssuer(),
                entity.getSubject(),
                entity.getProvider(),
                entity.getCreatedAt()
        );
    }

    public List<IdentityLink> entityListToDomainList(List<IdentityLinkEntity> entities) {
        return entities.stream().map(this::entityToDomain).toList();
    }
}
