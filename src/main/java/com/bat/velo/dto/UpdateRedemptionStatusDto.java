package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateRedemptionStatusDto extends BaseDto {
    protected long id;
    protected long status;
}
