package com.wahak.service.impl;

import com.wahak.entity.Chalak;
import com.wahak.entity.Order;
import com.wahak.entity.OrderRiderMapping;
import com.wahak.entity.Store;
import com.wahak.enums.OrderStatus;
import com.wahak.repository.ChalakRepository;
import com.wahak.repository.OrderRepository;
import com.wahak.repository.OrderRiderMappingRepository;
import com.wahak.repository.StoreRepository;
import com.wahak.service.OrderAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author krishna.meena
 */

@Service
public class OrderAssignmentImpl implements OrderAssignment {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ChalakRepository chalakRepository;

    @Autowired
    private OrderRiderMappingRepository orderRiderMappingRepository;

    @Override
    @Async
    public void assignOrder(Order order) {

        Store store=storeRepository.findById(order.getStoreId()).orElseGet(null);
        if(store == null) {
            throw new IllegalArgumentException("Invalid Store");
        }
        List<Chalak> riders=chalakRepository.findAvailableRiders(store.getId());
        if(riders == null || riders.size() <= 0) {
            throw new IllegalArgumentException("No Rider Available");
        }
        Chalak chalak= riders.stream().findFirst().get();
        OrderRiderMapping orderRiderMapping=new OrderRiderMapping();
        orderRiderMapping.setOrderStatus(OrderStatus.PENDING);
        orderRiderMapping.setRider(chalak);
        orderRiderMapping.setOrder(order);
        orderRiderMapping.setRiderId(chalak.getId());
        orderRiderMappingRepository.save(orderRiderMapping);
    }
}
