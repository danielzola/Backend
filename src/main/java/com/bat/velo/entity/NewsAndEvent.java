package com.bat.velo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "vlo_newsAndEvent")
public class NewsAndEvent extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "title")
    protected String title;
    
    @Column(name = "content")
    protected String content;
    
    @Column(name = "video_url")
    protected String videoUrl;

    @Column(name = "type")
    protected long type;
    @Column(name = "status")
    protected long status;


}
