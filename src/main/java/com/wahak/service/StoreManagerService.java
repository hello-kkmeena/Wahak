package com.wahak.service;

import com.wahak.dto.ItemDto;

import java.util.List;

/**
 * @author krishna.meena
 */
public interface StoreManagerService {
    List<ItemDto> getItems(String name, int pageNo);

    List<ItemDto> getStoreItems(String name, int pageNo);

    ItemDto createStoreItem(ItemDto item, Integer storeId);

    Boolean deleteStoreItem(Integer itemId);

    ItemDto updateStoreItem(ItemDto item, Integer itemId);
}
