package com.bat.velo.repository;

import com.bat.velo.entity.EmailTemplate;
import com.bat.velo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends CrudRepository<EmailTemplate, Long> {
    @Query(value = "select * from vlo_template where name= :name ",nativeQuery = true)
    EmailTemplate findByName(String name);
}