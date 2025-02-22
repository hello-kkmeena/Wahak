package com.wahak.service.impl;

import com.wahak.dto.ItemDto;
import com.wahak.entity.ItemCommon;
import com.wahak.entity.StoreItem;
import com.wahak.repository.ItemCommonRepository;
import com.wahak.repository.StoreItemRepository;
import com.wahak.service.ItemService;
import com.wahak.service.StoreService;
import com.wahak.utils.ItemUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author krishna.meena
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemCommonRepository itemCommonRepository;
    private final StoreItemRepository storeItemRepository;
    private final StoreService storeService;

    public ItemServiceImpl(ItemCommonRepository itemCommonRepository, StoreItemRepository storeItemRepository, StoreService storeService) {
        this.itemCommonRepository = itemCommonRepository;
        this.storeItemRepository = storeItemRepository;
        this.storeService = storeService;
    }

    @Override
    public ItemDto createItem(ItemDto itemDto, MultipartFile image) throws IOException {

        ItemCommon item= ItemUtils.convertDtoToItem(itemDto);
        item.setImage(image.getBytes());

        itemCommonRepository.save(item);

        itemDto.setId(item.getId());
        return itemDto;
    }

    @Override
    public ItemDto createItemStore(ItemDto item) {

        if(item.getStoreId() != null && item.getId() != null && isValidItem(item.getId())
                && storeService.isValidStore(item.getStoreId()) ) {
            StoreItem storeItem = ItemUtils.convertDtoToStoreItem(item);
            storeItem.setItemId(item.getId());
            storeItemRepository.save(storeItem);
            item.setStoreItemId(storeItem.getId());
            return item;
        }else {
          System.out.println("Invalid Item");
        }
        return null;
    }

    private boolean isValidItem(Integer id) {
        return itemCommonRepository.existsByIdAndIsActive(id,true);
    }
}
