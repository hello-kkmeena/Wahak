package com.wahak.service.impl;

import com.wahak.dto.ChalakDto;
import com.wahak.entity.Chalak;
import com.wahak.entity.OtpDetails;
import com.wahak.enums.OtpType;
import com.wahak.repository.OtpDetailsRepository;
import com.wahak.service.OtpService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

/**
 * @author krishna.meena
 */
@Service
public class OtpServiceImpl implements OtpService {

    private final OtpDetailsRepository otpDetailsRepository;

    public OtpServiceImpl(OtpDetailsRepository otpDetailsRepository) {
        this.otpDetailsRepository = otpDetailsRepository;
    }

    @Override
    public String generateRegistartionVerificationOtp(Chalak entity) {
        Integer otp=generateNumber();
        OtpDetails otp_details=new OtpDetails();
        otp_details.setOtp(otp);
        otp_details.setUserId(entity.getId());
        otp_details.setOtpType(OtpType.CHALAK_REGISTRATION);
        otp_details.setActive(true);
        otp_details.setExpiredAt(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(5));
        otpDetailsRepository.save(otp_details);
        return otp.toString();
    }

    @Override
    public boolean validateOtp(String otp, OtpType otpType, Integer userId) {

        OtpDetails otpDetails = otpDetailsRepository.findLatestUnusedOtp( userId,otpType).orElseGet(null);
        if(isValidOtp(otpDetails,otp) ) {
            otpDetails.setUsed(true);
            otpDetailsRepository.save(otpDetails);
            return true;
        }
        return false;
    }

    private boolean isValidOtp(OtpDetails otpDetails,String otp) {
        return otpDetails != null && otpDetails.getOtp().equals(Integer.parseInt(otp)) && otpDetails.getExpiredAt().isBefore(LocalDateTime.now(ZoneId.of("UTC")));
    }


    private synchronized Integer generateNumber() {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999
        System.out.println("Random Number: " + randomNumber);
        return randomNumber;
    }
}
