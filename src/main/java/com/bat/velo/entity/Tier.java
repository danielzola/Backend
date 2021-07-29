package com.bat.velo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vlo_tier")
public class Tier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "tier_name")
    private String tierName;
}
