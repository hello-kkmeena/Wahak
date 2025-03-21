package com.wahak.service;

import com.wahak.entity.Sanchaalaak;

import java.util.List;

/**
 * @author krishna.meena
 */
public interface SanchaalaakService {
    Sanchaalaak fetchSanchalak();

    Sanchaalaak createSanchalak(Sanchaalaak sanchaalaak);
}
