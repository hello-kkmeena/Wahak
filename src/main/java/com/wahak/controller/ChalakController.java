package com.wahak.controller;

import com.wahak.dto.ChalakDto;
import com.wahak.service.ChalakService;

import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author krishna.meena
 */
@RestController
@RequestMapping("/chalak")
public class ChalakController {

    private final ChalakService chalakService;

    public ChalakController(ChalakService chalakService) {
        this.chalakService = chalakService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody ChalakDto chalakDto) {

        Map<String,Object> respone=chalakService.login(chalakDto);

        if((boolean) respone.get("status")) {
            return ResponseEntity.ok(respone);
        }
        return ResponseEntity.internalServerError().body(respone);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOTP(@RequestBody ChalakDto chalakDto) {
        Map<String,Object> respone=chalakService.verifyOTP(chalakDto);

        if((boolean) respone.get("status")) {
            return ResponseEntity.ok(respone);
        }
        return ResponseEntity.internalServerError().body(respone);
    }

    @GetMapping("/chalak-order")
    public ResponseEntity<Map<String,Object>> getCommonItemItems(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false, defaultValue = "0") int pageNo) {
        return ResponseEntity.ok(chalakService.chalakOrder(name,pageNo));
    }

    @PostMapping("/{orderId}/update-order")
    public ResponseEntity<Object> updateOrder(@PathParam("orderId") Integer orderId) {
        Map<String,Object> respone=chalakService.updateOrderStatus(orderId);

        if((boolean) respone.get("status")) {
            return ResponseEntity.ok(respone);
        }
        return ResponseEntity.internalServerError().body(respone);
    }







}
