package com.wahak.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Sanchaalaak extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String phone;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "sanchalak_store_mapping",
            joinColumns = @JoinColumn(name = "sanchalak_id"),
            inverseJoinColumns = @JoinColumn(name = "store_id")
    )
    @JsonManagedReference
    private List<Store> stores;

}
