package com.bat.velo.repository;

import com.bat.velo.entity.ProductCatalogEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCatalogRepository extends CrudRepository<ProductCatalogEntity, Long> {

    @Query(value = "SELECT * FROM vlo_product_catalog fv where fv.name_catalog = :nameCatalog",
                nativeQuery=true
    )
    ProductCatalogEntity findCatalogByName(String nameCatalog);

}
