package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordRqDTO {
    
    protected String userId;
    protected String pass;
    protected String oldPass;
}
