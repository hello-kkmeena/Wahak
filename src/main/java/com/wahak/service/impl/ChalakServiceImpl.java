package com.wahak.service.impl;

import com.wahak.dto.ChalakDto;
import com.wahak.entity.Chalak;
import com.wahak.enums.OtpType;
import com.wahak.repository.ChalakRepository;
import com.wahak.service.ChalakService;
import com.wahak.service.OtpService;
import com.wahak.service.SMSService;
import com.wahak.utils.Chalaktils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author krishna.meena
 */

@Service
public class ChalakServiceImpl implements ChalakService {

    private final ChalakRepository chalakRepository;
    private final OtpService otpService;
    private final SMSService smsService;

    public ChalakServiceImpl(ChalakRepository chalakRepository, OtpService otpService, SMSService smsService) {
        this.chalakRepository = chalakRepository;
        this.otpService = otpService;
        this.smsService = smsService;
    }

    @Override
    @Transactional
    public ChalakDto create(ChalakDto chalak) {

        Chalak entity = Chalaktils.convertDtoToEntity(chalak);
        chalakRepository.save(entity);
        String otp=otpService.generateRegistartionVerificationOtp(entity);

        sendMessage(entity,otp);
        return null;
    }

    @Override
    public Boolean validateRegistrationOtp(ChalakDto optRequest) {

        Chalak chalak=chalakRepository.findById(optRequest.getId()).orElseGet(null);
        if(isValidchalakForRegistration(chalak)) {
            boolean isValid=otpService.validateOtp(optRequest.getOtp(), OtpType.CHALAK_REGISTRATION,chalak.getId());
            if(isValid) {
                chalak.markVerify();
                chalakRepository.save(chalak);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean enableChalak(Integer chalakId) {
        Chalak chalak=chalakRepository.findById(chalakId).orElseGet(null);
        if(isValidToEnable(chalak)) {
            chalak.doActivate();
            chalakRepository.save(chalak);
            return true;
        }
        return false;
    }

    @Override
    public Boolean blockChalak(Integer chalakId) {
        Chalak chalak=chalakRepository.findById(chalakId).orElseGet(null);
        if(chalak!=null) {
            chalak.doBlock();
            chalakRepository.save(chalak);
            return true;
        }
        return false;
    }

    @Override
    public Boolean disableChalak(Integer chalakId) {
        Chalak chalak=chalakRepository.findById(chalakId).orElseGet(null);
        if(chalak!=null) {
            chalak.deActivate();
            chalakRepository.save(chalak);
            return true;
        }
        return false;
    }

    @Override
    public Boolean unBlockChalak(Integer chalakId) {
        Chalak chalak=chalakRepository.findById(chalakId).orElseGet(null);
        if(chalak!=null) {
            chalak.unblock();
            chalakRepository.save(chalak);
            return true;
        }
        return false;
    }

    private boolean isValidToEnable(Chalak chalak) {
        if(chalak == null) {
            return false;
        }
        if(chalak.isBlocked()){
            return false;
        }
        if(chalak.getDeletedOn() != null) {
            return false;
        }
        return true;
    }

    private boolean isValidchalakForRegistration(Chalak chalak) {

        if(chalak == null)  {
            return false;
        }

        if(!chalak.isActive() && !chalak.isBlocked() && !chalak.isVerified()) {
            return true;
        }

        return false;

    }

    private void sendMessage(Chalak entity, String otp) {
        System.out.println("sending otp "+otp+" to "+entity.getMobile());
        String message="your Registration OTP is: "+otp;
        smsService.sendSMS(message,entity.getMobile());
    }
}
