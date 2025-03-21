package com.wahak.repository;

import com.wahak.entity.OrderInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author krishna.meena
 */
public interface OrderInvoiceRepository extends JpaRepository<OrderInvoice, Integer> {
}
