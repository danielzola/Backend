package com.bat.velo.repository;

import com.bat.velo.entity.ParameterEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends CrudRepository<ParameterEntity, Long> {

    @Query(value = "SELECT * FROM vlo_parameter fv where fv.paramname = :paramName", nativeQuery = true)
    ParameterEntity findByName(String paramName);
}
