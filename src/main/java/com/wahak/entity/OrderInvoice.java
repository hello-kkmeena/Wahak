package com.wahak.entity;

import com.wahak.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */
@Entity
@Getter
@Setter
public class OrderInvoice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double itemAmount;
    private Double discountAmount;
    private Double deliveryCharge;
    private Double taxAmount;
    private Double otherCharges;
    private Double totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private PaymentMode paymentMode;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;


}
