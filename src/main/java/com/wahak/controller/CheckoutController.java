package com.wahak.controller;

import com.wahak.dto.CheckoutRequestDto;
import com.wahak.dto.CheckoutResponseDto;
import com.wahak.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author krishna.meena
 */

@RestController
@RequestMapping("checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;


    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/")
    public ResponseEntity<CheckoutResponseDto> checkout(@RequestBody CheckoutRequestDto checkoutRequestDto) {
        return ResponseEntity.ok(checkoutService.checkout(checkoutRequestDto));
    }
}
