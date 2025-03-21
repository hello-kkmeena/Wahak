package com.wahak.controller;

import com.wahak.dto.OrderDto;
import com.wahak.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author krishna.meena
 */

@RestController
@RequestMapping("order")
public class OrderController {

    private final Logger log= LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        try {
            return ResponseEntity.ok(orderService.createOrder(orderDto));
        }catch (Exception e) {
            log.error("Exception while creating Order message :{} ",e.getMessage());
            e.printStackTrace();
            ResponseEntity.internalServerError().body("Internal Server Error Please Try again Message: "+e.getMessage());
        }
        return ResponseEntity.internalServerError().build();
    }
}
