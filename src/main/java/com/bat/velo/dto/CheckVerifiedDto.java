package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckVerifiedDto {
    
    protected String userId;
    protected String referalCode;
}