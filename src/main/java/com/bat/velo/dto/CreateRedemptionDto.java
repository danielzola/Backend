package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRedemptionDto extends BaseDto {
    protected String nameRedemption;
    protected String description;
    protected long redemPoint;
    protected long stock;
    protected long categoryId;
    protected long status;
}
