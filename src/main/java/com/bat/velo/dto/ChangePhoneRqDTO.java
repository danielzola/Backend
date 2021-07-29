package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePhoneRqDTO {
    
    protected String userId;
    protected String phoneNumber;
    protected String otp;
}
