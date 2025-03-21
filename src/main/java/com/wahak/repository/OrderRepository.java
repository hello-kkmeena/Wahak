package com.wahak.repository;

import com.wahak.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author krishna.meena
 */
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
