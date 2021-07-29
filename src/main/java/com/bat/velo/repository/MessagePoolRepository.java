package com.bat.velo.repository;

import com.bat.velo.entity.MessagePool;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagePoolRepository extends CrudRepository<MessagePool, Long>{
    
    @Query(nativeQuery = true,
            value = "select * from vlo_msgpool "
                    + "where status = 0 "
                    + "and channel = ?1 "
                    + "and next_try < ?2 "
                    + "order by id asc limit 50")
    List<MessagePool> findPool(String channel, Date now);
}
