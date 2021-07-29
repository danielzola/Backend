package com.bat.velo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vlo_parameter")
public class ParameterEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "paramname")
    protected String paramname;

    @Column(name = "paramvalue")
    protected String paramvalue;
}
