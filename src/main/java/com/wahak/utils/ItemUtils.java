package com.wahak.utils;

import com.wahak.dto.ItemDto;
import com.wahak.entity.ItemCommon;
import com.wahak.entity.StoreItem;
import org.springframework.beans.BeanUtils;

/**
 * @author krishna.meena
 */
public class ItemUtils {

    public static ItemCommon convertDtoToItem(ItemDto itemDto) {
        ItemCommon entity = new ItemCommon();
        BeanUtils.copyProperties(itemDto, entity);
        return entity;
    }

    public static StoreItem convertDtoToStoreItem(ItemDto item) {
        StoreItem storeItem = new StoreItem();
        BeanUtils.copyProperties(item, storeItem);
        return storeItem;
    }

    public static ItemDto convertItemCommonEntityToDto(ItemCommon item) {
        ItemDto dto = new ItemDto();
        BeanUtils.copyProperties(item, dto);
        return dto;
    }

    public static ItemDto convertEntityToDto(StoreItem item) {
        ItemDto dto = new ItemDto();
        BeanUtils.copyProperties(item, dto);
        return dto;
    }

    public static ItemDto convertStoreItemtoITemCard(StoreItem item) {

        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getItem().getName());
        dto.setImage(item.getItem().getImage());
        dto.setPrice(item.getPrice());
        dto.setDiscount(item.getDiscount());
        dto.setCategory(item.getItem().getCategory());
        dto.setQuantity(item.getQuantity()+" "+item.getQuantityType());
        dto.setDescription(item.getItem().getDescription());
        return dto;
    }
}
