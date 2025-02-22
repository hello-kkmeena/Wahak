package com.wahak.service.impl;

import com.wahak.dto.ConstantDto;
import com.wahak.entity.Constant;
import com.wahak.repository.StoreRepository;
import com.wahak.service.AppService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author krishna.meena
 */

@Service
public class AppServiceImpl implements AppService {


    private final StoreRepository storeRepository;

    public AppServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public List<ConstantDto> getAllCategory(Integer cityId) {
        
        List<Integer> cityStores=getCityStores(cityId);
        
        List<Integer> storeCategories=getStoreCategories(cityStores);

        List<Constant> constants=getConstants(storeCategories);
        
        return null;
    }

    private List<Constant> getConstants(List<Integer> storeCategories) {
        return null;
    }

    private List<Integer> getStoreCategories(List<Integer> cityStores) {
        return null;
    }

    private List<Integer> getCityStores(Integer cityId) {
        return storeRepository.findIdsByCityId(cityId);
    }
}
