package com.bat.velo.repository;

import com.bat.velo.entity.ProductRedemptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRedemptionRepository extends CrudRepository<ProductRedemptionEntity, Long> {

    @Query(value = "SELECT * FROM vlo_product_redemption fv where fv.name_redemption = :nameRedemption",
            nativeQuery = true
    )
    ProductRedemptionEntity findRedemptionByName(String nameRedemption);
}
