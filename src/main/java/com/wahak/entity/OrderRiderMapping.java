package com.wahak.entity;

import com.wahak.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */

@Entity
@Setter
@Getter
public class OrderRiderMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private OrderStatus orderStatus;


    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id",nullable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name = "rider_id", referencedColumnName = "id", nullable = false)
    private Chalak rider;
}
