package com.bat.velo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "vlo_redemption")
public class Redemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "id_user")
    protected String idUser;
//
//    @Column(name = "id_product")
//    protected Integer idProduct;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "redemp_time", length = 19)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss",timezone = "Asia/Jakarta")
    protected Date redempTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product")
    private ProductRedemptionEntity productRedemptionEntity;

}
