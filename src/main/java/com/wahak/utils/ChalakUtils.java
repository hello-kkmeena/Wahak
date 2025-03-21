package com.wahak.utils;

import com.wahak.dto.ChalakDto;
import com.wahak.entity.Chalak;
import org.springframework.beans.BeanUtils;

/**
 * @author krishna.meena
 */
public class ChalakUtils {

    public static Chalak convertDtoToEntity(ChalakDto chalakDto) {
        Chalak entity = new Chalak();
        BeanUtils.copyProperties(chalakDto, entity);
        return entity;
    }

    public static ChalakDto convertEntityToDto(Chalak chalak) {
        ChalakDto dto = new ChalakDto();
        BeanUtils.copyProperties(chalak, dto);
        return dto;
    }

    public static String CreateOtpMessage(Chalak chalak,String otp) {
        return "Your OTP is: " + otp;
    }

    public static void validateEntityForLogin(ChalakDto chalakDto) {

        if(chalakDto==null) {
            throw new RuntimeException("chalak is required");
        }
        if (chalakDto.getMobile() == null || chalakDto.getMobile().isEmpty()) {
            throw new RuntimeException("Mobile number is required");
        }

    }

    public static void validateEntityForVerifyOTP(ChalakDto chalakDto) {

        if(chalakDto==null) {
            throw new RuntimeException("chalak is required");
        }
        if (chalakDto.getMobile() == null || chalakDto.getMobile().isEmpty()) {
            throw new RuntimeException("Mobile number is required");
        }
        if (chalakDto.getOtp() == null || chalakDto.getOtp().isEmpty()) {
            throw new RuntimeException("OTP is required");
        }
    }
}
