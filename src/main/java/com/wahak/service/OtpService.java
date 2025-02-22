package com.wahak.service;

import com.wahak.dto.ChalakDto;
import com.wahak.entity.Chalak;
import com.wahak.enums.OtpType;
import jakarta.validation.constraints.Pattern;

/**
 * @author krishna.meena
 */
public interface OtpService {
    String generateRegistartionVerificationOtp(Chalak entity);

    boolean validateOtp(@Pattern(regexp = "^[1-9]\\d{3}$", message = "OTP must be a 4-digit number") String otp, OtpType otpType, Integer id);
}
