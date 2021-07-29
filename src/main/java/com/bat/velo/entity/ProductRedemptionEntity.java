package com.bat.velo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vlo_product_redemption")
public class ProductRedemptionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "code")
    protected String code;

    @Column(name = "name_redemption")
    protected String nameRedemption;

    @Column(name = "description")
    protected String description;

    @Column(name = "redemPoint")
    protected long redemPoint;

    @Column(name = "stock")
    protected long stock;

    @Column(name = "categoryId")
    protected long categoryId;

    @Column(name = "status")
    protected long status;

}
