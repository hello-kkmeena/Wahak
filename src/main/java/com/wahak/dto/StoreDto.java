package com.wahak.dto;

import com.wahak.entity.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author krishna.meena
 */
@Getter
@Setter
public class StoreDto {

    private int id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "cityId is required")
    private String cityId;

    @NotBlank(message = "state is required")
    private String state;

    @NotBlank(message = "country is required")
    private String country;

    @NotBlank(message = "zip is required")
    private String zip;

    private String phone;

    private LocalDateTime addedOn;
}
