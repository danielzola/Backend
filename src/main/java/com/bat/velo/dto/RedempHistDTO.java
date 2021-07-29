package com.bat.velo.dto;

import com.bat.velo.entity.ProductRedemptionEntity;
import com.bat.velo.entity.Redemption;
import com.bat.velo.repository.ProductRedemptionRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@SuperBuilder
public class RedempHistDTO {
    @JsonProperty(value = "RedemptionId")
    private long id;

    protected long status;
    protected String idUser;

    protected long idProduct;
    protected String nameRedemption;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss",timezone = "Asia/Jakarta")
    protected Date redempTime;
    protected String description;
    protected long redemPoint;
    protected long redemptQty;
}
