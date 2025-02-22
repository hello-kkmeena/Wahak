package com.wahak.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * @author krishna.meena
 */
public interface EmailService {
    void sendEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email, String subject, String content);
}
