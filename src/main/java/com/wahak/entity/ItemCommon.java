package com.wahak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */
@Entity
@Getter
@Setter
public class ItemCommon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String name;
    private String description;
    private String category;
    private String subCategory;
    private String brand;
    private String model;
    private String color;
    private String size;
    private String material;
    private String pattern;

    @Lob
    @Column(columnDefinition = "LONGBLOB")  // For MySQL, use LONGBLOB for large images
    private byte[] image;

}
