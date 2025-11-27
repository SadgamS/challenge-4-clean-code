package com.bo.store.usecase;

import com.bo.store.domain.model.User;
import com.bo.store.port.in.RegisterUserUseCase;
import com.bo.store.port.out.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserImpl implements RegisterUserUseCase {
    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserImpl(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("User already exists");
        }
        User u = User.newUser(username, passwordEncoder.encode(password), "ROLE_USER");
        userRepository.save(u);
    }
}
