package com.bat.velo.repository;

import com.bat.velo.entity.Files;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilesRepository extends CrudRepository<Files, Long> {
    Files findByForeignIdAndFileType(long foreignId, String fileType);

    @Query(value = "select * from vlo_files fv where fv.id = :idFiles and fv.file_type = :fileType", nativeQuery = true)
    Files findByIdAndFileType(@Param("idFiles") long idFiles, @Param("fileType") String fileType);

    @Query(value = "Select * from vlo_files fv where fv.foreign_id = :foreignId and fv.file_type = :fileType", nativeQuery = true)
    List<Files> findAllByForeignIdAndFileTypes(@Param("foreignId") long foreignId, @Param("fileType") String fileType);

}