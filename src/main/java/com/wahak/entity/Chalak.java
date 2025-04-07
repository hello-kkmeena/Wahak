package com.wahak.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */

@Getter
@Setter
@Entity
public class Chalak extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false,unique = true)
    private String mobile;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;
    private String cityId;
    private String state;
    private String country;
    private String zip;

    @Column(nullable = false)
    private boolean isVerified;


    @Column(nullable = false)
    private boolean isBlocked;

    public void markVerify() {
        this.isVerified=true;
    }

    public void doActivate() {
        super.isActive = true;
    }

    public void deActivate() {
        super.isActive = false;
    }

    public void doBlock() {
        this.isBlocked = true;
        this.isActive = false;
    }

    public void unblock() {
        if(this.isVerified == true ) {
            this.isBlocked = false;
            this.isActive=true;
        }else {
            throw new RuntimeException("Chalak is not verified yet");
        }
    }
}
