package com.bat.velo.repository;

import com.bat.velo.entity.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

    @Query(value = "SELECT * FROM vlo_product_category t where t.name_category = :nameCategory",
            nativeQuery=true
    )
    ProductCategory findByCategoryName(String nameCategory);

    @Query(value = "Select * from vlo_product_category fv where fv.name_category LIKE %:categoryName%",
            nativeQuery = true)
    List<ProductCategory> searchByCategoryName(@Param("categoryName") String categoryName);
}
