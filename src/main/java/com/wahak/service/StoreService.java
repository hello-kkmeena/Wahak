package com.wahak.service;

import com.wahak.dto.ItemDto;
import com.wahak.dto.StoreDto;
import com.wahak.entity.StoreItem;
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

    Integer getStoreIdByCity(Integer cityId);

    List<ItemDto> getStoreItem(Integer storeId);
}
