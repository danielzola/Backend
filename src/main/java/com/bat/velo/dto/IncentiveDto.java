package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncentiveDto implements Serializable  {

    private long id;
    
    protected String userId;
    protected String sourceUserId;
    protected String sourceUserName;
    
    protected Integer amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Jakarta")
    protected Date createTime;
    protected Integer trxType;

    protected Integer status;
    protected String statusName;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss", timezone = "Asia/Jakarta")
    protected Date transferTime;
}
