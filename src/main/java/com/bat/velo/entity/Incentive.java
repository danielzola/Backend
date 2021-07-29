package com.bat.velo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "vlo_incentive")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Incentive implements Serializable  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "user_id")
    protected long userId;
    
    @Column(name = "source_user_id")
    protected long sourceUserId;
    
    @Column(name = "amount")
    protected Integer amount;
    
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP) 
    protected Date createTime;
    
    @Column(name = "trx_type")
    protected Integer trxType;
    
    @Column(name = "status")
    protected Integer status;

    @Column(name = "account_no")
    protected String accountNo;
    
    @Column(name = "transfer_time")
    @Temporal(TemporalType.TIMESTAMP) 
    protected Date transferTime;
}
