package com.bat.velo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "vlo_userfile")
@SuperBuilder
@NoArgsConstructor
public class UserFile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "user_id")
    protected long userId;
    
    @Column(name = "file_data", columnDefinition = "LONGTEXT")
    protected String fileData;
    
    @Column(name = "file_type")
    protected String fileType;
    
    @Column(name = "file_name")
    protected String fileName;
}
