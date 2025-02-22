package com.wahak.service;

import com.wahak.dto.ConstantDto;

import java.util.List;

/**
 * @author krishna.meena
 */
public interface AppService {
    List<ConstantDto> getAllCategory(Integer cityId);
}
