package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OtpRqDto {
    
    protected String userId;
    protected String phoneNumber;
    protected Integer mode;
}
