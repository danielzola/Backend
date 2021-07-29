package com.bat.velo.repository;

import com.bat.velo.entity.ProductRedemptionEntity;
import com.bat.velo.entity.Redemption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedemptionRepository extends CrudRepository<Redemption, Long> {

    @Query(value = "select * from vlo_redemption where id_user=:idUser and id_product=:idProduct",nativeQuery = true)
    Redemption nativeByIdUserAndIdProduct(String idUser, long idProduct);

}
