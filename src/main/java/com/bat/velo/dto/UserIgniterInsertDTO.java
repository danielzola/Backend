package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;

@Data
public class UserIgniterInsertDTO extends BaseDto{
    
    private long id;
    
    @JsonProperty("userName")
    protected String userId;
    protected String pass;
    
    @JsonIgnore
    protected long roleCode;
    
    protected String name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected Date dateOfBirth;
    
    protected Integer gender;
    protected String province;
    protected String city;
    protected String address;
    protected String phoneNumber;
    
    @JsonIgnore
    protected String referalCode;
    
    protected String idCardNumber;
    protected String idCardImage;
    
    @JsonIgnore
    protected Integer isVerified;
    
    @JsonIgnore
    protected Integer isActive;

}
