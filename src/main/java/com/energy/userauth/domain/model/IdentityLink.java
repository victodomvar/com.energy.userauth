package com.energy.userauth.domain.model;

import java.time.Instant;

public class IdentityLink {
    private Long id;
    private Long userId;
    private String issuer;
    private String subject;
    private String provider;
    private Instant createdAt;

    public IdentityLink() {
    }

    public IdentityLink(Long id, Long userId, String issuer, String subject, String provider, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.issuer = issuer;
        this.subject = subject;
        this.provider = provider;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
