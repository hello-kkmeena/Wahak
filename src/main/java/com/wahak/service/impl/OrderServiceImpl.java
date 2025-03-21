package com.wahak.service.impl;

import com.wahak.dto.OrderDto;
import com.wahak.entity.Order;
import com.wahak.entity.OrderInvoice;
import com.wahak.entity.OrderItemMapping;
import com.wahak.entity.StoreItem;
import com.wahak.enums.OrderStatus;
import com.wahak.repository.OrderInvoiceRepository;
import com.wahak.repository.OrderItemMappingRepository;
import com.wahak.repository.OrderRepository;
import com.wahak.repository.StoreItemRepository;
import com.wahak.service.OrderAssignment;
import com.wahak.service.OrderService;
import com.wahak.utils.OrderUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author krishna.meena
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StoreItemRepository storeItemRepository;

    @Autowired
    private OrderInvoiceRepository orderInvoiceRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemMappingRepository orderItemMappingRepository;

    @Autowired
    private OrderAssignment orderAssignment;



    @Override
    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {


        validateOrder(orderDto);
        List<StoreItem> items= storeItemRepository.findByStoreIdAndItemIdIn(orderDto.getStoreId(),orderDto.getItemQuantity().keySet());

        double itemcount=items.size();

        Double totalItemPrice= OrderUtility.gettotalItemprice(items,orderDto);
        Double deliveryCharges= OrderUtility.getDeliveryCharge(items,orderDto);
        Double taxAmount= OrderUtility.getTaxOnItems(items,orderDto);
        Double discountAmount= OrderUtility.getDiscountOnItems(items,orderDto);
        Double otherCharges= OrderUtility.getOtherchargesAmount(items,orderDto);

        OrderInvoice invoice=new OrderInvoice();
        invoice.setItemAmount(totalItemPrice);
        invoice.setDiscountAmount(discountAmount);
        invoice.setDeliveryCharge(deliveryCharges);
        invoice.setTaxAmount(taxAmount);
        invoice.setOtherCharges(otherCharges);
        invoice.setTotalAmount(totalItemPrice+deliveryCharges+taxAmount+otherCharges-discountAmount);
        invoice=orderInvoiceRepository.save(invoice);


        Order order=new Order();
        order.setStoreId(orderDto.getStoreId());
//        order.setOrderInvoice(invoice);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setAddressId(orderDto.getAddressId());
        order.setTotalAmount(invoice.getTotalAmount());
        order.setActive(true);
        order=orderRepository.save(order);

        List<OrderItemMapping> orderItemMappings = new ArrayList<>();

        for(StoreItem item:items){
            OrderItemMapping orderItemMapping=new OrderItemMapping();

            orderItemMapping.setOrder(order);
            orderItemMapping.setItemId(item.getId());
            orderItemMapping.setQuantity(orderDto.getItemQuantity().get(item.getId()));
            orderItemMapping.setAmount(item.getPrice() * orderDto.getItemQuantity().get(item.getId()));
            orderItemMapping.setDiscount(item.getDiscount() * orderDto.getItemQuantity().get(item.getId()));

            orderItemMappings.add(orderItemMapping);
        }
        orderItemMappingRepository.saveAll(orderItemMappings);

        orderAssignment.assignOrder(order);
        orderDto.setId(order.getId());

        return orderDto;
    }

    @Override
    public OrderStatus getNextStatus(Order order) {

        if(order.getOrderStatus() == OrderStatus.PENDING) {
            return OrderStatus.ACCEPTED;
        }
        if (order.getOrderStatus() == OrderStatus.ACCEPTED) {
            return OrderStatus.ON_THE_WAY;
        }
        if (order.getOrderStatus() == OrderStatus.ON_THE_WAY) {
            return OrderStatus.REACHED;
        }
        if (order.getOrderStatus() == OrderStatus.REACHED) {
            return OrderStatus.DELIVERED;
        }
        return null;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    private void validateOrder(OrderDto orderDto) {

        if(orderDto.getItemQuantity() == null || orderDto.getItemQuantity().isEmpty()) {
            throw new IllegalArgumentException("Item Ids are required");
        }
        if(orderDto.getPrice() == null || orderDto.getPrice() <= 0) {
            throw new IllegalArgumentException("Price is required");
        }

        if(orderDto.getStoreId() == null) {
            throw new IllegalArgumentException("StoreId is required");
        }

        // validate delivery charges

        // validate for tex

        // validate discount

        //
    }
}
