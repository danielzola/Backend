package com.bat.velo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRsDTO {
    
    protected String accessToken;
    protected String refreshToken;
    protected UserDTO user;
}
