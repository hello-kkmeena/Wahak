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
}
