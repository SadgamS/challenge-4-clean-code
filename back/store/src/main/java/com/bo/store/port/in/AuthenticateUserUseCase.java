package com.bo.store.port.in;

import com.bo.store.dto.AuthRequest;
import com.bo.store.dto.AuthResponse;

public interface AuthenticateUserUseCase {
    AuthResponse authenticate(AuthRequest request);
}
