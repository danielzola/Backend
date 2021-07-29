package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchipelagoProvinceDTO {

    @JsonProperty("provinceId")
    protected String idProvince;
    protected String provinceName;
}

