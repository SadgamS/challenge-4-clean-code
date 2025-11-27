package com.bo.store.adapter.out.persistence;

import com.bo.store.adapter.out.persistence.jpa.UserEntity;
import com.bo.store.adapter.out.persistence.jpa.UserJpaRepository;
import com.bo.store.domain.model.User;
import com.bo.store.port.out.UserRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort {
    private final UserJpaRepository jpa;

    public UserPersistenceAdapter(UserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .passwordHash(user.getPasswordHash())
                .roles(user.getRoles())
                .createdAt(user.getCreatedAt() == null ? java.time.Instant.now() : user.getCreatedAt())
                .build();
        UserEntity saved = jpa.save(entity);
        return User.of(saved.getId(), saved.getUsername(), saved.getPasswordHash(), saved.getRoles(), saved.getCreatedAt());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpa.findByUsername(username).map(e -> User.of(e.getId(), e.getUsername(), e.getPasswordHash(), e.getRoles(), e.getCreatedAt()));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpa.existsByUsername(username);
    }
}
