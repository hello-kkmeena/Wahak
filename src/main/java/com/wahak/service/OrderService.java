package com.wahak.service;

import com.wahak.dto.OrderDto;
import com.wahak.entity.Order;
import com.wahak.enums.OrderStatus;

import java.util.Map;

/**
 * @author krishna.meena
 */
public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    OrderStatus getNextStatus(Order order);

    void save(Order order);
}
