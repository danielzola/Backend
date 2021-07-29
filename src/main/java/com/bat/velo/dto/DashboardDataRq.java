package com.bat.velo.dto;

import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DashboardDataRq {

    protected String name;
    protected Map<String, Object> params;
}
