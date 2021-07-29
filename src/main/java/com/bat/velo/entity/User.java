package com.bat.velo.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "vlo_user")
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "email_address")
    protected String userId;
    
    @Column(name = "password")
    protected String pass;
    
    @Column(name = "role_code")
    protected Integer roleCode;
    
    @Column(name = "name")
    protected String name;
    
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    protected Date dateOfBirth;
    
    @Column(name = "gender")
    protected Integer gender;
    
    @Column(name = "province")
    protected String province;
    
    @Column(name = "city")
    protected String city;
    
    @Column(name = "address")
    protected String address;

    @Column(name = "district")
    protected String district;
    
    @Column(name = "phone_number")
    protected String phoneNumber;
    
    @Column(name = "referal_code")
    protected String referalCode;
    
    @Column(name = "parent_referal_code")
    protected String parentReferalCode;
    
    @Column(name = "id_card_number")
    protected String idCardNumber;
    
    @Column(name = "id_card_image")
    protected String idCardImage;
    
    @Column(name = "is_verified")
    protected Integer isVerified;
    
    @Column(name = "is_active")
    protected Integer isActive;
    
    @Column(name="verified_time")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date verifiedTime;
    
    @Column(name = "token")
    protected String token;

    @Column(name = "total_point")
    private Integer totalPoint;

    @Column(name = "current_point")
    private Integer currentPoint;

    @Column(name = "tier_id")
    private Integer tierId;

    @Column(name = "current_incentive_ammount")
    private Double currentIncentiveAmmount;

    @Column(name = "eligible_for_igniter")
    private Integer eligibleForIgniter;

    @Column(name = "invitation_status")
    private Integer invitationStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "accept_date", length = 19)
    private Date acceptDate;

}
