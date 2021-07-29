package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateRedemptionStatusDto extends BaseDto {
    protected long productRedemptionId;
    protected long status;
}
