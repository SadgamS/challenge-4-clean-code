package com.bo.store.domain.model;

import java.time.Instant;
import java.util.Objects;

public final class User {
    private final Long id;
    private final String username;
    private final String passwordHash;
    private final String roles;
    private final Instant createdAt;

    private User(Long id, String username, String passwordHash, String roles, Instant createdAt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roles = roles;
        this.createdAt = createdAt;
    }

    public static User newUser(String username, String passwordHash, String roles) {
        return new User(null, username, passwordHash, roles, Instant.now());
    }

    public static User of(Long id, String username, String passwordHash, String roles, Instant createdAt) {
        return new User(id, username, passwordHash, roles, createdAt);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRoles() {
        return roles;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return Objects.equals(id, u.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
