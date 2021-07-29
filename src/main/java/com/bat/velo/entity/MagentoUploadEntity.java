package com.bat.velo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "vlo_magentotrx")
public class MagentoUploadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;

    @Column(name = "idx_number")
    protected long idxNumber;

    @Column(name = "seller_referral_code")
    protected String sellerReferralCode;

    @Column(name = "external_order_id")
    protected String externalOrderID;

    @Column(name = "order_start_date")
    protected String orderStartDate;

    @Column(name = "sku_number")
    protected String skuNumber;

    @Column(name = "product_name")
    protected String productName;

    @Column(name = "unit_price_currency")
    protected String unitPriceCurrency;

    @Column(name = "unit_price")
    protected long unitPrice;

    @Column(name = "quantity")
    protected long quantity;

    @Column(name = "order_amount_currency")
    protected String orderAmountCurrency;

    @Column(name = "order_amount")
    protected long orderAmount;

    @Column(name = "order_total_currency")
    protected String orderTotalCurrency;

    @Column(name = "order_total")
    protected long orderTotal;

    @Column(name = "generated_id")
    protected String generatedId;

}
