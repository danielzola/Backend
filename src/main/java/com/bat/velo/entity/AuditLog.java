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

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "vlo_auditlog")
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;
    
    protected String category;
    
    @Column(name="audit_type")
    protected String auditType;
    
    @Column(columnDefinition = "TEXT")
    protected String prev;
    
    @Column(columnDefinition = "TEXT")
    protected String current;
    
    @Column(name="user_id")
    protected String userId;
    
    @Column(name="audit_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss",timezone = "Asia/Jakarta")
    protected Date auditTime;
}
