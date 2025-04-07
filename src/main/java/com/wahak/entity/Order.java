package com.wahak.entity;

import com.wahak.enums.OrderStatus;
import com.wahak.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author krishna.meena
 */
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private OrderStatus orderStatus;

    private Integer storeId;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<OrderInvoice> orderInvoice;

//    @Column(name = "user_id",nullable = false)
//    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = true, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id",insertable=true, updatable=true)
    private Address address;


}
