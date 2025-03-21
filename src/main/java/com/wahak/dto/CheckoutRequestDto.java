package com.wahak.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author krishna.meena
 */

@Getter
@Setter
public class CheckoutRequestDto {
    private Integer storeId;
    private Integer addressId;
    private Map<Integer,Integer> itemQuantity;
}
