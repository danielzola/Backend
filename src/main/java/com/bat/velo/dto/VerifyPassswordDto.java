package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyPassswordDto {
    protected String userId;
    protected String token;
    protected String password;

}
