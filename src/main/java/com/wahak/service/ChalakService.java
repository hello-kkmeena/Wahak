package com.wahak.service;

import com.wahak.dto.ChalakDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.Map;

/**
 * @author krishna.meena
 */
public interface ChalakService {
    ChalakDto create(@Valid ChalakDto chalak);

    Boolean validateRegistrationOtp( ChalakDto optRequest);

    Boolean enableChalak(@NotBlank Integer chalakId);

    Boolean blockChalak(Integer chalakId);

    Boolean disableChalak(Integer chalakId);

    Boolean unBlockChalak(Integer chalakId);

    Map<String,Object> login(ChalakDto chalakDto);

    Map<String, Object> verifyOTP(ChalakDto chalakDto);

    Map<String, Object> chalakOrder(String name, int pageNo);

    Map<String, Object> updateOrderStatus(Integer orderId);
}
