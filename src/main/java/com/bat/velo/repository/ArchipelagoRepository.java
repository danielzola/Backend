package com.bat.velo.repository;

import com.bat.velo.entity.Archipelago;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArchipelagoRepository extends CrudRepository<Archipelago, String> {


    @Query(value = "SELECT * from vlo_archipelago vw where CHARACTER_LENGTH(kode)=2 order by kode",nativeQuery = true)
    List<Archipelago>findProvince();

    @Query(value = "SELECT * from vlo_archipelago vw where CHARACTER_LENGTH(kode)=:length and kode like :value% order by kode",nativeQuery = true)
    List<Archipelago>findCityOrDistrict(@Param("length")long length,@Param("value")String value);
}