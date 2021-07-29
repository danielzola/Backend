package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateStatusCampaignDto {
    protected Long campaignId;
    protected Integer statusId;
    protected String createdBy;
}