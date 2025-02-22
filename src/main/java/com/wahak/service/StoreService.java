package com.wahak.service;

import com.wahak.dto.StoreDto;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author krishna.meena
 */
public interface StoreService {
    StoreDto createStore(@Valid StoreDto store);

    Boolean deleteStore(Integer storeId);

    List<StoreDto> getAllStores();

    boolean isValidStore(Integer storeId);
}
