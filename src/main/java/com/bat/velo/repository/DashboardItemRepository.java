package com.bat.velo.repository;

import com.bat.velo.entity.DashboardItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardItemRepository extends CrudRepository<DashboardItem, Long>{
    
    DashboardItem findByName(String name);
}
