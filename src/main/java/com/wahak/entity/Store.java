package com.wahak.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author krishna.meena
 */
@Entity
@Getter
@Setter
public class Store extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String address;

    private String city;

    private Integer cityId;

    private String state;

    private String country;

    private String zip;

    private String phone;


    @ManyToMany(mappedBy = "stores", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Sanchaalaak> admins;


}
