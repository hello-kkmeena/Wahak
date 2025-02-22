package com.wahak.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author krishna.meena
 */

@Getter
@Setter
@Entity
public class Constant extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Integer constId;

    private Integer parentId;

    private String parentName;

    private String name;

    private String identifier;

    private String comment;

    private Integer sequence;

}
