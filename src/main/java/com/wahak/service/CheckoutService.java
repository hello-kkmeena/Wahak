package com.wahak.service;

import com.wahak.dto.CheckoutRequestDto;
import com.wahak.dto.CheckoutResponseDto;

/**
 * @author krishna.meena
 */
public interface CheckoutService {
    CheckoutResponseDto checkout(CheckoutRequestDto checkoutRequestDto);
}
