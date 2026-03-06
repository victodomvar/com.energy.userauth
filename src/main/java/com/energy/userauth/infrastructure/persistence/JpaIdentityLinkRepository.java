package com.energy.userauth.infrastructure.persistence;

import com.energy.userauth.infrastructure.persistence.entity.IdentityLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaIdentityLinkRepository extends JpaRepository<IdentityLinkEntity, Long> {
    List<IdentityLinkEntity> findByUser_Id(Long userId);
    Optional<IdentityLinkEntity> findByIssuerAndSubject(String issuer, String subject);
}
