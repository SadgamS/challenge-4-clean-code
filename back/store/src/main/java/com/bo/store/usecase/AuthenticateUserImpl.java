package com.bo.store.usecase;

import com.bo.store.domain.model.User;
import com.bo.store.dto.AuthRequest;
import com.bo.store.dto.AuthResponse;
import com.bo.store.port.in.AuthenticateUserUseCase;
import com.bo.store.port.out.UserRepositoryPort;
import com.bo.store.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserImpl implements AuthenticateUserUseCase {
    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticateUserImpl(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        return new AuthResponse(token, "Bearer", jwtUtil.getExpirySeconds());
    }
}
