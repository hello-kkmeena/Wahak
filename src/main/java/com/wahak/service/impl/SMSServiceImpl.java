package com.wahak.service.impl;

import com.wahak.service.SMSService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author krishna.meena
 */
@Service
public class SMSServiceImpl implements SMSService {
    @Override
    @Async
    public void sendSMS(String message, String mobile) {
        System.out.println("SMS Send to mobile: "+mobile+" with message: "+message);
    }
}
