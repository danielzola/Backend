package com.bat.velo.repository;

import com.bat.velo.entity.Files;
import com.bat.velo.entity.NewsAndEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsAndEventRepository extends CrudRepository<NewsAndEvent, Long> {

}