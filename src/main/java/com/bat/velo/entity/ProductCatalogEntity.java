package com.bat.velo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vlo_product_catalog")
public class ProductCatalogEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "code")
    protected String codeCatalog;

    @Column(name = "name_catalog")
    protected String nameCatalog;

    @Column(name = "description")
    protected String description;

    @Column(name = "shop_url")
    protected String shopUrl;

    @Column(name = "category_id")
    protected long categoryId;

    @Column(name = "status")
    protected long status;
}
