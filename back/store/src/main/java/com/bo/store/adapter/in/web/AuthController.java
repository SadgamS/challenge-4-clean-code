package com.bo.store.adapter.in.web;

import com.bo.store.dto.AuthRequest;
import com.bo.store.dto.AuthResponse;
import com.bo.store.port.in.AuthenticateUserUseCase;
import com.bo.store.port.in.RegisterUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegisterUserUseCase registerUser;
    private final AuthenticateUserUseCase authenticateUser;

    public AuthController(RegisterUserUseCase registerUser, AuthenticateUserUseCase authenticateUser) {
        this.registerUser = registerUser;
        this.authenticateUser = authenticateUser;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AuthRequest req) {
        registerUser.register(req.getUsername(), req.getPassword());
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> token(@RequestBody AuthRequest req) {
        AuthResponse resp = authenticateUser.authenticate(req);
        return ResponseEntity.ok(resp);
    }
}
