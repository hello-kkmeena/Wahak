package com.wahak.service;

import com.wahak.dto.ChalakDto;
import com.wahak.entity.Sanchaalaak;

import java.util.List;

/**
 * @author krishna.meena
 */
public interface SanchaalaakService {
    Sanchaalaak fetchSanchalak();
    Sanchaalaak createSanchalak(Sanchaalaak sanchaalaak);
    List<ChalakDto> getChalak(String mobile, int pageNo, Boolean isActive, Boolean isBlocked);
}
