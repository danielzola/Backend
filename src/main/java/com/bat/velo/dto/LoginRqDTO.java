package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRqDTO {
    
    protected String userId;
    protected String pass;
}

