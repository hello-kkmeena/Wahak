package com.wahak.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author krishna.meena
 */

@Setter
@Getter
public class OrderDto {

    private Integer id;
    Map<Integer,Integer> itemQuantity;
    private Double price;
    private Double discount;
    private Double tax;
    private Double taxAmount;
    private Double discountAmount;
    private Double deliveryCharge;
    private Double otherCharges;
    private Double totalAmount;
    private Double payableAmount;
    private Integer quantity;
    private Integer storeId;
    private Integer addressId;
}
