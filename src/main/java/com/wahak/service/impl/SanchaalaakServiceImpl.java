package com.wahak.service.impl;

import com.wahak.entity.Sanchaalaak;
import com.wahak.repository.SanchaalaakRepository;
import com.wahak.service.SanchaalaakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author krishna.meena
 */

@Service
public class SanchaalaakServiceImpl implements SanchaalaakService {

    @Autowired private SanchaalaakRepository sanchaalaakRepository;;

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
}
