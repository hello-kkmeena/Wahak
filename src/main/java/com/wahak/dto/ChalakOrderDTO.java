package com.wahak.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 *
 * @author krishna.meena
 *
 */

@Getter
@Setter
public class ChalakOrderDTO {

     private String orderId;

     private String amount;

     private String customerName;

     private String PaymentStatus;

     private LocalDateTime orderTime;

     private String orderStatus;

     private String address;

     private String lat;

     private String lang;

}
