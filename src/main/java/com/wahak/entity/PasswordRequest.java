package com.wahak.entity;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */

@Getter
@Setter
public class PasswordRequest {

    private String password;
}
