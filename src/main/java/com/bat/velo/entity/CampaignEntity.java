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
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "vlo_campaign")
public class CampaignEntity extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    protected long id;
    
    @Column(name = "user_id")
    protected Integer userId;
    
    @Column(name = "campaign_name")
    protected String campaignName;
    
    @Column(name = "campaign_description")
    protected String campaignDescription;

    @Column(name="start_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss",timezone = "Asia/Jakarta")
    protected Date startDate;

    @Column(name="end_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd kk:mm:ss",timezone = "Asia/Jakarta")
    protected Date endDate;
    
    @Column(name = "campaign_status")
    protected Integer campaignStatus;
    
    @Column(name = "point_reward")
    protected Integer pointReward;
}
