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

    @ManyToOne(fetch = FetchType.LAZY) // Lazy for Better Performance 🔥
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private ItemCommon item;

    @Column(nullable = false)
    private Double price;

    private Double discount;

    private String description;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private QuantityType quantityType;

    private Boolean isAvailable;

    private String stock;
    private String status;
    private Integer createdBy;
    private Integer updatedBy;
    private String deletedBy;
    private Boolean isOnSale;
    private Boolean isOutOfStock;
    private Boolean isOnOffer;

    private Integer sequence;


}
