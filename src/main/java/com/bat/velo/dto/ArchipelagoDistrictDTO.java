package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchipelagoDistrictDTO {

    @JsonProperty("districtId")
    protected String idDistrict;
    protected String districtName;
}

