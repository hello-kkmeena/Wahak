package com.wahak.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */

@Getter
@Setter
public class CheckoutAmountDto {

    private int itemcount;

    private Double totalamount;
    private Double discountamount;
    private Double payableamountwithouttax;
    private Double tax;
    private Double taxamount;
    private Double delivercharge;
    private Double packingcharge;
    private Double othercharge;
    private Double payableamount;
}
