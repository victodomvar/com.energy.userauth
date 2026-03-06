package com.energy.userauth.presentation.mapper;

import com.energy.userauth.domain.model.IdentityLink;
import com.energy.userauth.openapi.model.CreateIdentityLinkRequest;
import com.energy.userauth.openapi.model.IdentityLinkDto;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class IdentityLinkApiMapper {

    public IdentityLink createRequestToDomain(CreateIdentityLinkRequest request) {
        if (request == null) {
            return null;
        }
        return new IdentityLink(
                null,
                null,
                request.getIssuer(),
                request.getSubject(),
                request.getProvider(),
                null
        );
    }

    public IdentityLinkDto domainToApiDto(IdentityLink identityLink) {
        if (identityLink == null) {
            return null;
        }
        IdentityLinkDto dto = new IdentityLinkDto()
                .id(identityLink.getId())
                .userId(identityLink.getUserId())
                .issuer(identityLink.getIssuer())
                .subject(identityLink.getSubject())
                .provider(identityLink.getProvider());
        if (identityLink.getCreatedAt() != null) {
            dto.setCreatedAt(OffsetDateTime.ofInstant(identityLink.getCreatedAt(), ZoneOffset.UTC));
        }
        return dto;
    }

    public List<IdentityLinkDto> domainListToApiList(List<IdentityLink> links) {
        return links.stream().map(this::domainToApiDto).toList();
    }
}
