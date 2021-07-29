package com.bat.velo.repository;

import com.bat.velo.entity.CampaignEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends CrudRepository<CampaignEntity, Long> {
    
//    @Query(value = "Select * from vlo_campaign t where t.campaign_name LIKE %:campaignName%", nativeQuery = true)
//    List<CampaignEntity> findByCampaignNameByLikes(@Param("campaignName") String campaignName);
//
//    @Query(value = "Select * from vlo_campaign t where t.start_date=:startDate", nativeQuery = true)
//    List<CampaignEntity> findByStartDateeByLikes(@Param("startDate") String startDate);
//
//    @Query(value = "Select * from vlo_campaign t where t.end_date=:endDate", nativeQuery = true)
//    List<CampaignEntity> findByEndDateeByLikes(@Param("endDate") String endDate);

    @Query(value = "Select * from vlo_campaign t where t.campaign_name LIKE %:campaignName% and t.start_date = :startDate or t.end_date = :endDate", nativeQuery = true)
    List<CampaignEntity> findBy(@Param("campaignName") String campaignName, @Param("startDate") String startDate, @Param("endDate") String endDate);

    List<CampaignEntity>findAllByUserId(@Param("userId")Integer userId);
}
