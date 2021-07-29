package com.bat.velo.upload.repository;

import com.bat.velo.entity.MagentoUploadEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagentoUploadRespository extends JpaRepository<MagentoUploadEntity, Long> {

    @Query(value = "Select * from vlo_magentotrx fv where fv.seller_referral_code = :sellerReferralCode",
            nativeQuery = true)
    List<MagentoUploadEntity> findMagentoBySellerReferralCode(
            @Param("sellerReferralCode") String sellerReferralCode,
            Pageable pageable);
}
