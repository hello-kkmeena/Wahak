package com.wahak.service;

import com.wahak.entity.Chalak;
import com.wahak.enums.OtpType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

/**
 * @author krishna.meena
 */
public interface OtpService {
    String generateRegistartionVerificationOtp(Chalak entity);

    boolean sendOtpWithTimeOut(String mobile, Integer id, OtpType otpType, LocalDateTime expiredDate);

    boolean validateOtp(@Pattern(regexp = "^[1-9]\\d{3}$", message = "OTP must be a 4-digit number") String otp, OtpType otpType, Integer id);

    void sendOtp(@NotBlank(message = "Mobile number is required") @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits") String mobile,Integer userId, OtpType otpType);

    boolean verifyOtp(@NotBlank(message = "Mobile number is required") @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits") String mobile, Integer id, String otp, OtpType otpType);
}
