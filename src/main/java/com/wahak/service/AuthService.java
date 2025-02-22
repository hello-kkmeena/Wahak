package com.wahak.service;

import com.wahak.dto.ResetPasswordRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * @author krishna.meena
 */
public interface AuthService {
    boolean isValidToken(String token);

    Object createPassword(@NotBlank @Valid String token, @Valid ResetPasswordRequest request);
}
