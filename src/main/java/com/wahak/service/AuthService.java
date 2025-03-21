package com.wahak.service;

import com.wahak.dto.ResetPasswordRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

/**
 * @author krishna.meena
 */
public interface AuthService {
    boolean isValidToken(String token);

    Object createPassword(@NotBlank @Valid String token, @Valid ResetPasswordRequest request);

    Map<String,Object> login(@NotBlank @Valid String mobile);

    Map<String, Object> verifyOtp(String otpToken, @NotBlank @Valid String otp);
}
