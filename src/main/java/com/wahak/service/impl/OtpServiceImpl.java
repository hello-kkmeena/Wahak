package com.wahak.service.impl;

import com.wahak.entity.Chalak;
import com.wahak.entity.OtpDetails;
import com.wahak.enums.OtpType;
import com.wahak.repository.OtpDetailsRepository;
import com.wahak.service.OtpService;
import com.wahak.service.SMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

/**
 * @author krishna.meena
 */
@Service
public class OtpServiceImpl implements OtpService {

    private final OtpDetailsRepository otpDetailsRepository;
    private final SMSService smsService;
    private final Logger logger = LoggerFactory.getLogger(OtpServiceImpl.class);

    public OtpServiceImpl(OtpDetailsRepository otpDetailsRepository, SMSService smsService) {
        this.otpDetailsRepository = otpDetailsRepository;
        this.smsService = smsService;
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

        try {
            OtpDetails otpDetails = otpDetailsRepository.findLatestUnusedOtp(userId, otpType).orElseGet(null);
            if (isValidOtp(otpDetails, otp)) {
                otpDetails.setUsed(true);
                otpDetailsRepository.save(otpDetails);
                return true;
            }
        }catch (Exception e){
            logger.error("Error while validating otp",e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional
    @Async
    public void sendOtp(String mobile, Integer id, OtpType otpType) {
        OtpDetails otpDetails = genrateOtp(mobile, otpType, id);
        otpDetails.setExpiredAt(LocalDateTime.now(ZoneId.of("UTC")).plusMinutes(5));
        String message = "Your OTP For Login is " + otpDetails.getOtp();
        smsService.sendSMS(message,mobile);
        otpDetailsRepository.save(otpDetails);
        logger.info("OTP sent to mobile number: {}", mobile);
    }


    @Override
    @Transactional
    public boolean sendOtpWithTimeOut(String mobile, Integer id, OtpType otpType,LocalDateTime expiredDate) {

        OtpDetails otpDetails = genrateOtp(mobile, otpType, id);
        otpDetails.setExpiredAt(expiredDate);
        String message=otpDetails.getOtp() +" is Your OTP.please do not share it with anyone";
        smsService.sendSMS(message,mobile);
        otpDetailsRepository.save(otpDetails);
        logger.info("OTP sent to mobile number: {}", mobile);

        return true;
    }

    @Override
    public boolean verifyOtp(String mobile, Integer id, String otp, OtpType otpType) {

        boolean flag=false;
        OtpDetails otpDetails = otpDetailsRepository.findLatestUnusedOtp(id,otpType).orElseGet(null);
        if(isValidOtp(otpDetails,otp)) {
            otpDetails.setUsed(true);
            flag= true;
        }
        otpDetails.setAttempts(otpDetails.getAttempts() == null ? 1 :otpDetails.getAttempts() + 1);
        otpDetailsRepository.save(otpDetails);
        return flag;
    }

    private OtpDetails genrateOtp(String mobile, OtpType otpType, Integer id) {
        Integer otp = generateNumber();
        OtpDetails otp_details = new OtpDetails();
        otp_details.setOtp(otp);
        otp_details.setUserId(id);
        otp_details.setMobile(mobile);
        otp_details.setOtpType(otpType);
        otp_details.setActive(true);
        return otp_details;
    }

    private boolean isValidOtp(OtpDetails otpDetails,String otp) {

        int maxCount= otpDetails.getMaxAttempts() != null ? otpDetails.getMaxAttempts() : 5;
        return otpDetails != null
                && !otpDetails.isUsed()
                && otpDetails.getOtp().equals(Integer.parseInt(otp))
                && otpDetails.getExpiredAt().isAfter(LocalDateTime.now(ZoneId.of("UTC")))
                && (otpDetails.getAttempts() == null ? 0 :otpDetails.getAttempts()) < maxCount;
    }


    private synchronized Integer generateNumber() {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // Generates a number between 1000 and 9999
        System.out.println("Random Number: " + randomNumber);
        return randomNumber;
    }
}
