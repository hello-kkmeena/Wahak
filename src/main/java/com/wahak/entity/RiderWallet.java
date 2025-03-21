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

    private Double amount;

    private Double maxAmount;

    private Integer riderId;

    @Id
    @OneToOne
    @JoinColumn(name = "riderId", referencedColumnName = "id", insertable = false, updatable = false)
    private Chalak rider;
}
