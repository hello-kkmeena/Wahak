package com.wahak.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */
@Getter
@Setter
public class AddressDto {

    private Integer id;
    private String address;
    private String pincode;
    private String city;
    private String landmark;
    private String lat;
    private String lang;
}
