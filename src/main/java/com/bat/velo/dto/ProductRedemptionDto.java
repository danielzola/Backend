package com.bat.velo.dto;

import com.bat.velo.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class ProductRedemptionDto extends BaseEntity {


    protected String code;

    protected String nameRedemption;

    protected String description;

    protected long redemPoint;

    protected long stock;

    @Column(name = "categoryId")
    protected long categoryId;

    @Column(name = "status")
    protected long status;

}
