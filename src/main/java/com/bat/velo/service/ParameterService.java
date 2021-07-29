package com.bat.velo.service;

import com.bat.velo.entity.ParameterEntity;
import com.bat.velo.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParameterService {

    @Autowired
    protected ParameterRepository parameterRepository;

    public ParameterEntity getParameterByName(String name) throws Exception {
        ParameterEntity getParam = parameterRepository.findByName(name);
        if (getParam == null) throw new Exception("Parameter Not Found!");
        return getParam;
    }
}
