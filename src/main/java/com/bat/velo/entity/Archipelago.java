package com.bat.velo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vlo_archipelago")
public class Archipelago {
    @Id
    @Column(name = "kode")
    private String idProvince;

    @Column(name = "nama")
    private String provinceName;

}

