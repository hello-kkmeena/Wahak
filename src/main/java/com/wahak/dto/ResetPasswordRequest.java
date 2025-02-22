package com.wahak.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */
@Getter
@Setter
public class ResetPasswordRequest {

    @NotBlank(message = "password is required")
    private String password;
}
