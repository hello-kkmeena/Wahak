package com.wahak.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */

@Entity
@Getter
@Setter
public class RiderStoreAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer riderId;
    private Integer storeId;
    private Boolean isAvailable;
}
