package com.bat.velo.dto;

import com.bat.velo.entity.Tier;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data
@NoArgsConstructor
public class UserDTO extends BaseDto {
    
    private long id;
    
    @JsonProperty("userName")
    protected String userId;
    protected String pass;
    
    protected Integer roleCode;
    protected String name;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected Date dateOfBirth;
    
    protected Integer gender;
    protected String province;
    protected String city;
    protected String address;
    protected String district;
    protected String phoneNumber;
    protected String referalCode;
    protected String parentReferalCode;
    protected String parentName;
    
    protected String idCardNumber;
    protected String idCardImage;
    protected Integer isVerified;
    protected Integer isActive;
    protected Integer totalPoint;
    protected Integer current_point;
    protected Integer tierId;
    protected String tierName;
    private Double currentIncentiveAmmount;
    private Integer eligibleForIgniter;
    private Integer invitationStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date acceptDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected Date verifiedTime;

    private Integer numOfFueler;
    
    public void setId(Object data) {
        
        if (data instanceof BigInteger)
            id = ((BigInteger) data).longValue();
        else if (data instanceof Integer)
            id = ((Integer) data).longValue();
        else if (data != null)
            id = new Long(String.valueOf(data)); 
    }
    
    public void setNumOfFueler(Object data) {
        
        if (data instanceof BigInteger)
            numOfFueler = ((BigInteger) data).intValue();
        else if (data != null) 
            numOfFueler = new Integer(String.valueOf(data));
    }
}
