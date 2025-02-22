package com.wahak.service.impl;

import com.wahak.pojo.EmailSenderPojo;
import com.wahak.service.EmailSender;
import com.wahak.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Immutable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author krishna.meena
 */

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailSender emailSender;

    public EmailServiceImpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    public void sendPasswordResetEmail(String email, String token) {
        String resetUrl = "http://localhost:8081/reset-password?token=" + token;
        // Implement email sending logic
        System.out.println("Sending password reset email to: " + email);
        System.out.println("Reset URL: " + resetUrl);
    }

    @Async
    public void sendVerificationEmail(String email, String token) {
        String verificationUrl = "http://yourdomain.com/verify-account?token=" + token;
        // Implement email sending logic using JavaMailSender
        System.out.println("Sending verification email to: " + email);
        System.out.println("Verification URL: " + verificationUrl);
    }

    @Override
    @Async
    public void sendEmail(String email, String subject, String content) {

        System.out.println("Sending Set Password email to: " + email);
        EmailSenderPojo sendePojo= EmailSenderPojo.builder()
                .to(List.of(email))
                .subject(subject)
                .content(content)
                .build();
        emailSender.sendEmail(sendePojo);
    }
}
