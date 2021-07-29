package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActiveInActiveUserDTO {
    
    protected String userId;
    protected String status;
    protected String updatedBy;
}
