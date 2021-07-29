package com.bat.velo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "vlo_msgpool")
public class MessagePool {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;
    
    protected String channel;
    protected String receiver;
    protected String subject;
    protected String msg;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_try")
    protected Date nextTry;
    
    @Column(name = "max_try")
    protected int maxTry;
    
    protected int status;
}
