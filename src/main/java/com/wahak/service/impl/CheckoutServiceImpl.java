package com.wahak.service.impl;

import com.wahak.constant.Constant;
import com.wahak.dto.CheckoutAmountDto;
import com.wahak.dto.CheckoutRequestDto;
import com.wahak.dto.CheckoutResponseDto;
import com.wahak.dto.ItemDto;
import com.wahak.entity.StoreItem;
import com.wahak.repository.StoreItemRepository;
import com.wahak.service.CheckoutService;
import com.wahak.utils.ItemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krishna.meena
 */

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    private StoreItemRepository storeItemRepository;

    @Override
    public CheckoutResponseDto checkout(CheckoutRequestDto checkoutRequestDto) {
       
        ValidateCheckoutRequest(checkoutRequestDto);
        List<StoreItem> items=storeItemRepository.findByStoreIdAndItemIdIn(checkoutRequestDto.getStoreId()
                ,checkoutRequestDto.getItemQuantity().keySet());
        List<ItemDto> itemCards=new ArrayList<>();

        if(items == null || items.size() <= 0) {
            throw new IllegalArgumentException("Invalid Items");
        }

        for(StoreItem item:items){
            itemCards.add(ItemUtils.convertStoreItemtoITemCard(item));
        }
        CheckoutResponseDto response=new CheckoutResponseDto();
        response.setItems(itemCards);
        response.setAmounts(calculateAmounts(items,checkoutRequestDto));
        return response;
    }

    private CheckoutAmountDto calculateAmounts(List<StoreItem> items, CheckoutRequestDto checkoutRequestDto) {
        CheckoutAmountDto amounts=new CheckoutAmountDto();
        amounts.setItemcount(items.size());
        amounts.setTotalamount(items.stream().mapToDouble(item -> item.getPrice() * checkoutRequestDto.getItemQuantity().get(item.getId()) ).sum());
        amounts.setDiscountamount(items.stream().mapToDouble(item -> item.getDiscount() * checkoutRequestDto.getItemQuantity().get(item.getId())).sum());
        amounts.setDelivercharge(Double.parseDouble(Constant.DEFAULT_DELIVERY_CHARGE+""));
        amounts.setPayableamount(amounts.getTotalamount() + amounts.getDelivercharge() - amounts.getDiscountamount());
        return amounts;
    }

    private void ValidateCheckoutRequest(CheckoutRequestDto checkoutRequestDto) {
        if(checkoutRequestDto.getStoreId() == null) {
            throw new IllegalArgumentException("StoreId is required");
        }
        if(checkoutRequestDto.getAddressId() == null) {
            throw new IllegalArgumentException("AddressId is required");
        }
        if(checkoutRequestDto.getItemQuantity() == null || checkoutRequestDto.getItemQuantity().isEmpty()) {
            throw new IllegalArgumentException("ItemIds is required");
        }
    }
}
