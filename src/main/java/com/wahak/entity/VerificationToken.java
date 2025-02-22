package com.wahak.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * @author krishna.meena
 */

@Getter
@Setter
public class VerificationToken {

    private String token;
    private User user;
    private Instant expiryDate;

    private Boolean isUsed;

}
