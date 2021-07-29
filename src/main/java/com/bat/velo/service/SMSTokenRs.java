package com.bat.velo.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSTokenRs {

    protected String Result;
    protected String scope;
    protected String type;
    protected String expires_in;
    protected String access_token;
    protected String refresh_token;    
    
    protected String result;
    protected String ReasonCode;
    protected String UserSpecificReserved;
}
