package com.bat.velo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vlo_product_category")
public class ProductCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name_category")
    protected String nameCategory;

    @Column(name = "description")
    protected String description;
}
