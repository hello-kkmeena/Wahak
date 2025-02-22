package com.wahak.entity;

import com.wahak.enums.QuantityType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */

@Entity
@Getter
@Setter
public class StoreItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer storeId;

    @Column(nullable = false)
    private Integer itemId;

    private String price;

    private String discount;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private QuantityType quantityType;

    private Boolean isAvailable;
    private Boolean isActive;

    private String stock;
    private String status;
    private Integer createdBy;
    private Integer updatedBy;
    private String deletedBy;

    private Boolean isFeatured;
    private Boolean isPopular;
    private Boolean isTrending;
    private Boolean isDiscounted;
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

}
