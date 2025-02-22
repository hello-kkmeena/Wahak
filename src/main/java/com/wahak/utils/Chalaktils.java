package com.wahak.utils;

import com.wahak.dto.ChalakDto;
import com.wahak.entity.Chalak;
import org.springframework.beans.BeanUtils;

/**
 * @author krishna.meena
 */
public class Chalaktils {

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
}
