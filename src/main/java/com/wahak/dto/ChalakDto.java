package com.wahak.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ChalakDto {

    private Integer id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "mobile is required")
    private String mobile;

    @NotBlank(message = "address is required")
    private String address;
    private String city;
    private String cityId;
    private String state;
    private String country;
    private String zip;
    private boolean isVerified;
    private boolean isBlocked;
    private boolean isActive;

    @Pattern(regexp = "^[1-9]\\d{3}$", message = "OTP must be a 4-digit number")
    private String otp;

}
