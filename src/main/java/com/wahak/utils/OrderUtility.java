package com.wahak.utils;

import com.wahak.constant.Constant;
import com.wahak.dto.OrderDto;
import com.wahak.entity.StoreItem;

import java.util.List;

/**
 * @author krishna.meena
 */
public class OrderUtility {

    public static Double getDiscountOnItems(List<StoreItem> items, OrderDto orderDto) {
        return items.parallelStream()
                .mapToDouble(item ->
                        item.getDiscount() * orderDto.getItemQuantity().get(item.getId())
                )
                .sum();
    }

    public static Double gettotalItemprice(List<StoreItem> items, OrderDto orderDto) {
        return items.parallelStream()
                .mapToDouble(item ->
                        item.getPrice() * orderDto.getItemQuantity().get(item.getId())
                )
                .sum();
    }

    public static Double getTaxOnItems(List<StoreItem> items, OrderDto orderDto) {
        return 0d;
    }

    public static Double getDeliveryCharge(List<StoreItem> items, OrderDto orderDto) {
        return Constant.DEFAULT_DELIVERY_CHARGE;
    }

    public static Double getOtherchargesAmount(List<StoreItem> items, OrderDto orderDto) {
        return 0d;
    }
}
