package com.bat.velo.repository;

import com.bat.velo.entity.RedemptionStatusEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedemptionStatusRepository extends CrudRepository<RedemptionStatusEntity, Long> {
}
