package com.wahak.service.impl;

import com.wahak.constant.Constant;
import com.wahak.dto.ChalakDto;
import com.wahak.entity.Chalak;
import com.wahak.entity.Sanchaalaak;
import com.wahak.repository.ChalakRepository;
import com.wahak.repository.SanchaalaakRepository;
import com.wahak.service.SanchaalaakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * @author krishna.meena
 */

@Service
public class SanchaalaakServiceImpl implements SanchaalaakService {

    @Autowired private SanchaalaakRepository sanchaalaakRepository;
    @Autowired private ChalakRepository chalakRepository;

    @Override
    public Sanchaalaak fetchSanchalak() {
        int id=1;
        Optional<Sanchaalaak> sanchaalaakOpt=sanchaalaakRepository.findById(id);
        Sanchaalaak sanchaalaak=sanchaalaakOpt.isPresent() ? sanchaalaakOpt.get() : null;
        if(sanchaalaak != null) {
            sanchaalaak.getStores();
        }
        return sanchaalaak;

    }

    @Override
    public Sanchaalaak createSanchalak(Sanchaalaak sanchaalaak) {
        return sanchaalaakRepository.save(sanchaalaak);
    }

    @Override
    public List<ChalakDto> getChalak(String mobile, int pageNo, Boolean isActive, Boolean isBlocked) {

        Pageable pageable = PageRequest.of(pageNo, Constant.RIDER_DEFAULT_PAGE_SIZE);
        List<Chalak> chalaks=chalakRepository.getChalak(mobile,isActive,isBlocked,pageable);
        List<ChalakDto> dtos=chalaks.stream().map(chalak -> {
            ChalakDto dto=new ChalakDto();
            dto.setId(chalak.getId());
            dto.setName(chalak.getName());
            dto.setMobile(chalak.getMobile());
            dto.setAddress(chalak.getAddress());
            dto.setEmail(chalak.getEmail());
            dto.setActive(chalak.isActive());
            dto.setVerified(chalak.isVerified());
            dto.setBlocked(chalak.isBlocked());
            return dto;
        }).toList();
        return dtos;
    }
}
