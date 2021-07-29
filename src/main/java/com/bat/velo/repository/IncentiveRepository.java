package com.bat.velo.repository;

import com.bat.velo.entity.Incentive;
import com.bat.velo.entity.Redemption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncentiveRepository extends CrudRepository<Incentive, Long> {

    List<Incentive> findAllByUserId(long userId);
}
