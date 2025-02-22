package com.wahak.service;

import com.wahak.pojo.EmailSenderPojo;

/**
 * @author krishna.meena
 */
public interface EmailSender {
    void sendEmail(EmailSenderPojo sendePojo);
}
