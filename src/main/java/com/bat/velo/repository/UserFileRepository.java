package com.bat.velo.repository;

import com.bat.velo.entity.UserFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileRepository extends CrudRepository<UserFile, Long>{
    
    UserFile findByUserIdAndFileType(Long userId, String fileType);
}
