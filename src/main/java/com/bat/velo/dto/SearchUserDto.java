package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchUserDto {

    protected String createdBy;
    protected String roleCode;
    protected String filter;
    protected String parentReferalCode;
    protected Integer isActive;
    protected Integer isVerified;

}
