package com.wahak.repository;

import com.wahak.entity.OrderItemMapping;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author krishna.meena
 */
public interface OrderItemMappingRepository extends JpaRepository<OrderItemMapping, Integer> {
}
