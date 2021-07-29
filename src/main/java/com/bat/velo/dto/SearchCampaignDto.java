package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class SearchCampaignDto {
    protected String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Jakarta")
    protected SimpleDateFormat startDateFrom;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Jakarta")
    protected SimpleDateFormat startDateTo;

}
