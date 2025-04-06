package com.wahak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */

@Setter
@Getter
@Entity
public class RiderWallet extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double amount;

    private Double maxAmount;

    @OneToOne
    @JoinColumn(name = "rider_id", referencedColumnName = "id",nullable = false)
    private Chalak rider;
}
