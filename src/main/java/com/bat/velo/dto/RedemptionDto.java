package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class RedemptionDto {

    @JsonProperty("userId")
    protected String idUser;

    protected Integer idProduct;

    protected Integer status;
}
