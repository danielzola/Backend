package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchipelagoCityDTO {

    @JsonProperty("cityId")
    protected String idCity;
    protected String cityName;
}

