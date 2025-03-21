package com.wahak.service.impl;

import com.wahak.dto.ItemDto;
import com.wahak.entity.ItemCommon;
import com.wahak.entity.StoreItem;
import com.wahak.enums.QuantityType;
import com.wahak.repository.ItemCommonRepository;
import com.wahak.repository.StoreItemRepository;
import com.wahak.service.StoreManagerService;
import com.wahak.utils.ItemUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krishna.meena
 */

@Service
public class StoreManagerServiceImpl implements StoreManagerService {

    private static final int pageSize = 20;

    @Autowired private ItemCommonRepository itemCommonRepository;
    @Autowired private StoreItemRepository StoreItemRepository;
    @Autowired
    private StoreItemRepository storeItemRepository;


    public void createStoreManager() {
        System.out.println("StoreManagerServiceImpl");
    }

    @Override
    public List<ItemDto> getItems(String name,int page) {

        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                Sort.by("name").descending()
        );
        List<ItemCommon> commonItems= itemCommonRepository.findByActiveAndNameContainsIgnoreCase(name,pageable).getContent();
        List<ItemDto> items = new ArrayList<>();

        for(ItemCommon item : commonItems) {
            ItemDto dto=ItemUtils.convertItemCommonEntityToDto(item);
            items.add(dto);
        }
        return items;
    }

    @Override
    public List<ItemDto> getStoreItems(String name, int pageNo) {

        Pageable pageable = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by("name").descending()
        );

        List<StoreItem> items= StoreItemRepository.findByNameContainsIgnoreCase(name, pageable).getContent();
        List<Integer> itemIds = items.stream().map(StoreItem::getItem).map(ItemCommon::getId).toList();

        List<ItemCommon> commonItems = itemCommonRepository.findByIdInOrderbyName(itemIds);
        List<ItemDto> list=commonItems.stream().map(ItemUtils::convertItemCommonEntityToDto).toList();

        for(ItemDto item : list) {
            for(StoreItem storeItem : items) {
                if(item.getId().equals(storeItem.getItem().getId())) {
                    item.setStoreItemId(storeItem.getId());
                    BeanUtils.copyProperties(storeItem, item);
                    break;
                }
            }
        }

        return list;
    }

    @Override
    public ItemDto createStoreItem(ItemDto item, Integer storeId) {

        ItemCommon common=itemCommonRepository.findById(item.getId()).orElse(null);

        if(common==null) {
            return null;
        }
        StoreItem storeItem = new StoreItem();

        storeItem.setStoreId(item.getStoreId());
        storeItem.setQuantity(Double.valueOf(item.getQuantity()));
        storeItem.setPrice(item.getPrice());
        storeItem.setDiscount(item.getDiscount());
        storeItem.setId(item.getStoreItemId());
        storeItem.setQuantityType(common.getQuantityType() != null ? common.getQuantityType() : QuantityType.KG);
        storeItem.setItem(common);
        storeItem.setActive(true);


        storeItemRepository.save(storeItem);

        return null;
    }

    @Override
    public Boolean deleteStoreItem(Integer itemId) {
        StoreItem storeItem = storeItemRepository.findById(itemId).orElse(null);
        if(storeItem!=null) {
            storeItem.setActive(false);
            storeItemRepository.save(storeItem);
            return true;
        }
        return false;
    }

    @Override
    public ItemDto updateStoreItem(ItemDto item, Integer itemId) {

        StoreItem storeItem = storeItemRepository.findById(itemId).orElse(null);
        if(storeItem!=null && storeItem.isActive()) {
            ItemDto dto=new ItemDto();
            storeItem.setIsAvailable(item.getIsAvailable());
            storeItemRepository.save(storeItem);
            ItemCommon common=storeItem.getItem();
            dto.setId(storeItem.getId());
            dto.setStoreItemId(common.getId());
            dto.setName(common.getName());
            dto.setPrice(storeItem.getPrice());
            dto.setDiscount(storeItem.getDiscount());
            dto.setQuantity(storeItem.getQuantity()+" " + storeItem.getQuantityType().name() );
            dto.setStoreId(storeItem.getStoreId());
            dto.setImage(common.getImage());
            dto.setIsAvailable(storeItem.getIsAvailable());
            return dto;
        }else {
            throw new RuntimeException("Invalid Item");
        }

    }

}
