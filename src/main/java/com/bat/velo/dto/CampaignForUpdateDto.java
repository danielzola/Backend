package com.bat.velo.dto;

import com.bat.velo.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CampaignForUpdateDto extends BaseDto {

    protected long campaignId;
    protected Integer userId;
    protected String campaignName;
    protected String campaignDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Jakarta")
    protected Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Jakarta")
    protected Date endDate;

    protected Integer campaignStatus;
    protected Integer pointReward;
}
