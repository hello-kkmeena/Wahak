package com.wahak.dto;

import com.wahak.entity.StoreItem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author krishna.meena
 */
@Getter
@Setter
public class CheckoutResponseDto {

    private List<ItemDto> items;
    private CheckoutAmountDto amounts;

}
