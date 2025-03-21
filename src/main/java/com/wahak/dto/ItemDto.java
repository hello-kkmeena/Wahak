package com.wahak.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ItemDto {

    private Integer id;
    private String name;
    private String description;
    private String category;
    private String subCategory;
    private String brand;
    private String model;
    private String color;
    private String size;
    private String material;
    private String pattern;

    private Integer storeItemId;
    private Integer storeId;

    private Double price;
    private Double discount;

    private String quantity;
    private String stock;
    private String status;
    private Integer createdBy;
    private Integer updatedBy;
    private String deletedBy;
    private Boolean isDeleted;
    private Boolean isFeatured;
    private Boolean isPopular;
    private Boolean isTrending;
    private Boolean isDiscounted;
    private Boolean isAvailable;
    private Boolean isOutOfStock;
    private Boolean isOnSale;
    private Boolean isOnDiscount;
    private Boolean isOnTrending;
    private Boolean isOnPopular;
    private Boolean isOnFeatured;
    private Boolean isOnNewArrival;
    private Boolean isOnBestSeller;
    private Boolean isOnTopRated;
    private Boolean isOnRecommended;
    private Boolean isOnOffer;
    private Boolean isOnDeal;
    private Boolean isOnClearance;
    private Boolean isOnBulk;
    private Boolean isOnCombo;
    private Boolean isOnGift;
    private Boolean isOnFree;
    private Boolean isOnCashback;
    private Boolean isOnExchange;
    private Boolean isOnReturn;
    private Boolean isOnWarranty;
    private Boolean isOnGuarantee;
    private Boolean isOnEMI;
    private Boolean isOnCOD;
    private Boolean isOnPrepaid;

    private byte[] image;
}
