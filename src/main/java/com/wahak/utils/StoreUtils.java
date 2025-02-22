package com.wahak.utils;

import com.wahak.dto.StoreDto;
import com.wahak.entity.Store;

import java.util.List;

/**
 * @author krishna.meena
 */
public class StoreUtils {


    public static Store ConvertDtoToEntity(StoreDto storeDto) {
        Store store = new Store();
        store.setName(storeDto.getName());
        store.setAddress(storeDto.getAddress());
        store.setCity(storeDto.getCity());
        store.setCountry(storeDto.getCountry());
        store.setZip(storeDto.getZip());
        store.setPhone(storeDto.getPhone());

        return store;
    }



    public static StoreDto ConvertEntityToDto(Store store) {
        StoreDto storeDto = new StoreDto();
        storeDto.setId(store.getId());
        storeDto.setName(store.getName());
        storeDto.setAddress(store.getAddress());
        storeDto.setCity(store.getCity());
        storeDto.setCountry(store.getCountry());
        storeDto.setZip(store.getZip());
        storeDto.setPhone(store.getPhone());
        storeDto.setAddedOn(store.getCreatedAt());
        return storeDto;
    }
}
