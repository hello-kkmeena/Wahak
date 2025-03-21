package com.wahak.service.impl;

import com.wahak.dto.ItemDto;
import com.wahak.dto.StoreDto;
import com.wahak.entity.ItemCommon;
import com.wahak.entity.Store;
import com.wahak.entity.StoreItem;
import com.wahak.repository.StoreItemRepository;
import com.wahak.repository.StoreRepository;
import com.wahak.service.StoreService;
import com.wahak.utils.ItemUtils;
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
    private final StoreItemRepository storeItemRepository;

    public StoreServiceImpl(StoreRepository storeRepository, StoreItemRepository storeItemRepository) {
        this.storeRepository = storeRepository;
        this.storeItemRepository = storeItemRepository;
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

    @Override
    public Integer getStoreIdByCity(Integer cityId) {
        Store store=storeRepository.findFirstByCityId(cityId);
        return store!=null?store.getId():null;
    }

    @Override
    public List<ItemDto> getStoreItem(Integer storeId) {

        List<ItemDto> itmDtoList = new ArrayList<>();
        List<StoreItem> storeItems=storeItemRepository.findAvailableItemofStoreforSanchalak(storeId);

        for(StoreItem item:storeItems) {
            ItemDto dto=new ItemDto();
            ItemCommon common=item.getItem();
            dto.setId(item.getId());
            dto.setStoreItemId(common.getId());
            dto.setName(common.getName());
            dto.setPrice(item.getPrice());
            dto.setDiscount(item.getDiscount());
            dto.setQuantity(item.getQuantity()+" " + item.getQuantityType().name() );
            dto.setStoreId(storeId);
            dto.setImage(common.getImage());
            dto.setIsAvailable(item.getIsAvailable());
            itmDtoList.add(dto);
        }

        return itmDtoList;
    }
}
