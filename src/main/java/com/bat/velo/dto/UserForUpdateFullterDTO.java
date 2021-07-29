package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Blob;

@Data
public class UserForUpdateFullterDTO {
    private long id;
    protected String userId;
    protected String pass;
    @JsonIgnore
    protected int roleCode;
    protected String name;
    protected java.sql.Date  date_of_birth;
    protected Integer gender;
    protected String province;
    protected String city;
    protected String address;
    protected String phoneNumber;
    @JsonIgnore
    protected String referalCode;
    protected String idCardNumber;
    protected String idCardImage;
    protected Integer isVerified;

}
