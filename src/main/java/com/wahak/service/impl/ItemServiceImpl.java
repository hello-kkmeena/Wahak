package com.wahak.service.impl;

import com.wahak.dto.ItemDto;
import com.wahak.entity.ItemCommon;
import com.wahak.entity.StoreItem;
import com.wahak.enums.QuantityType;
import com.wahak.repository.ItemCommonRepository;
import com.wahak.repository.StoreItemRepository;
import com.wahak.service.ItemService;
import com.wahak.service.StoreService;
import com.wahak.utils.ItemUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.wahak.constant.Constant.HOME_PAGE_PAGINATED_ITEM_COUNT;

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

        ItemCommon itemC=itemCommonRepository.findById(item.getId()).orElse(null);

        if(item.getStoreId() != null && item.getId() != null && isValidItem(itemC)
                && storeService.isValidStore(item.getStoreId()) ) {
            StoreItem storeItem = ItemUtils.convertDtoToStoreItem(item);
            storeItem.setId(null);
            storeItem.setActive(true);
            storeItem.setQuantity(5.0);
            storeItem.setQuantityType(QuantityType.KG);
            storeItem.setItem(itemC);
            storeItemRepository.save(storeItem);
            item.setStoreItemId(storeItem.getId());
            return item;
        }else {
          System.out.println("Invalid Item");
        }
        return null;
    }

    @Override
    public List<ItemDto> getItemStore(String name, Integer cityId, Integer addressStoreId, Integer pageNo) {

        Pageable pageable = PageRequest.of(
                pageNo,
                HOME_PAGE_PAGINATED_ITEM_COUNT
        );
        List<ItemDto> itmDtoList = new ArrayList<>();
        if(addressStoreId == null && cityId != null) {
            addressStoreId = storeService.getStoreIdByCity(cityId);
        }
        if(addressStoreId != null) {
            List<StoreItem> items=null;
            if(StringUtils.isBlank(name)) {
                items = storeItemRepository.findAvailableItemofStore(addressStoreId,pageable);;
            }else {
                items = storeItemRepository.findByNameContainsIgnoreCase1(name,addressStoreId,pageable).getContent();
            }
            // create a list of ItemDto from the list of StoreItem using ItemUtils
            for(StoreItem item:items) {
                itmDtoList.add(ItemUtils.convertStoreItemtoITemCard(item));
            }
        }
        return itmDtoList;
    }

    private boolean isValidItem(ItemCommon item) {
        return item!= null && item.isActive();
    }
}
