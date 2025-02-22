package com.wahak.service.impl;

import com.wahak.dto.StoreDto;
import com.wahak.entity.Store;
import com.wahak.repository.StoreRepository;
import com.wahak.service.StoreService;
import com.wahak.utils.StoreUtils;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author krishna.meena
 */

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public StoreDto createStore(StoreDto storeDto) {
        Store store= StoreUtils.ConvertDtoToEntity(storeDto);
        storeRepository.save(store);
        storeDto.setId(store.getId());
        return storeDto;
    }

    @Override
    public Boolean deleteStore(Integer storeId) {
        Store store = storeRepository.findById(storeId).orElse(null);
        if(store!=null){
            store.delete();
            storeRepository.save(store);
            return true;
        }
        return false;
    }

    @Override
    public List<StoreDto> getAllStores() {

        List<Store> stores = storeRepository.findAll();

        List<StoreDto> dtos=new ArrayList<>();
        for(Store store:stores){
            dtos.add(StoreUtils.ConvertEntityToDto(store));
        }
        return dtos;
    }

    @Override
    public boolean isValidStore(Integer storeId) {
        return storeRepository.existsStoreByIdAndIsActive(storeId,true);

    }
}
