package com.bo.store.port.out;

import com.bo.store.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
