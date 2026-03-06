package com.energy.userauth.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    // Legacy column retained for DB compatibility during migration from local auth.
    @Column(name = "password")
    private String legacyPassword;

    private static final String LEGACY_PASSWORD_PLACEHOLDER = "EXTERNAL_IDP_MANAGED";

    public UserEntity() {
    }

    public UserEntity(Long id, String userName, String email, String status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();
        if (createdAt == null) {
            createdAt = now;
        }
        updatedAt = now;
        if (legacyPassword == null || legacyPassword.isBlank()) {
            legacyPassword = LEGACY_PASSWORD_PLACEHOLDER;
        }
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
        if (legacyPassword == null || legacyPassword.isBlank()) {
            legacyPassword = LEGACY_PASSWORD_PLACEHOLDER;
        }
    }
}
