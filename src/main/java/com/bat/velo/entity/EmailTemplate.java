package com.bat.velo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vlo_template")
public class EmailTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;

    @Column(name = "name")
    protected String name;

    @Column(name = "value")
    protected String htmlTags;
}
