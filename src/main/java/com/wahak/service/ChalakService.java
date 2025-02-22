package com.wahak.service;

import com.wahak.dto.ChalakDto;
import jakarta.validation.Valid;

/**
 * @author krishna.meena
 */
public interface ChalakService {
    ChalakDto create(@Valid ChalakDto chalak);

    Boolean validateRegistrationOtp(@Valid ChalakDto optRequest);

    Boolean enableChalak(Integer chalakId);

    Boolean blockChalak(Integer chalakId);

    Boolean disableChalak(Integer chalakId);

    Boolean unBlockChalak(Integer chalakId);
}
