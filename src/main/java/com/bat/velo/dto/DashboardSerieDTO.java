package com.bat.velo.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class DashboardSerieDTO {

    protected List<Object> values;
    protected String label;
}
