package com.bat.velo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "vlo_tokenisation")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tokenisation implements Serializable  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "user_id")
    protected long userId;
    
    @Column(name = "token_type")
    protected Integer tokenType;
    
    @Column(name = "token_number")
    protected String tokenNumber;
    
    @Column(name = "expired_time")
    @Temporal(TemporalType.TIMESTAMP) 
    protected Date expiredTime;
    
    @Column(name = "phone_number")
    protected String phoneNumber;
    
    @Column(name = "email")
    protected String email;
}
