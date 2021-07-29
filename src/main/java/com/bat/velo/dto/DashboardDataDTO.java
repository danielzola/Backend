package com.bat.velo.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class DashboardDataDTO {

    protected String type;
    protected Object singleValue;
    protected List<String> timeLabels;
    protected List<DashboardSerieDTO> series;
}
