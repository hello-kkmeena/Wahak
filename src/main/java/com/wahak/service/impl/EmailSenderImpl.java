package com.wahak.service.impl;

import com.wahak.pojo.EmailSenderPojo;
import com.wahak.service.EmailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author krishna.meena
 */

@Service
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;

    public EmailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(EmailSenderPojo sendePojo) {
        System.out.println("Email sent to "+String.join(", ",sendePojo.getTo()));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(String.join(", ",sendePojo.getTo()));
        message.setSubject(sendePojo.getSubject());
        message.setText(sendePojo.getContent());
        message.setFrom("krishna.ai.test@gmail.com");
//        mailSender.send(message);
    }
}
