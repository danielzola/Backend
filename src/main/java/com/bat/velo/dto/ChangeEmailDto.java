package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeEmailDto {

    protected String userId;
    protected String email;

}
