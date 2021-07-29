package com.bat.velo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "vlo_redemption_status")
public class RedemptionStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;

//    @Column(name = "redemption_id")
//    protected long productRedemptionId;

    @Column(name = "status")
    protected long status;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "redemption_id", referencedColumnName = "id")
    protected Redemption redemption;
}
