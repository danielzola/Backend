package com.bat.velo.upload.repository;

import com.bat.velo.entity.MagentotrxMetadataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagentotrxMetadataRepository extends JpaRepository<MagentotrxMetadataEntity, Long> {
}
