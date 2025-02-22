package com.wahak.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author krishna.meena
 */
@Entity
@Getter
@Setter
public class UserTokenMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    private Integer userId;

    private String type;

    private LocalDateTime expireAt;

    private boolean isExpired;
}
