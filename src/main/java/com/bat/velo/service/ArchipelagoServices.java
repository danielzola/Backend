package com.bat.velo.service;

import com.bat.velo.dto.ArchipelagoCityDTO;
import com.bat.velo.dto.ArchipelagoDistrictDTO;
import com.bat.velo.dto.ArchipelagoProvinceDTO;
import com.bat.velo.entity.Archipelago;
import com.bat.velo.repository.ArchipelagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArchipelagoServices {
    @Autowired
    protected ArchipelagoRepository archipelagoRepository;

    public List<ArchipelagoProvinceDTO> findProvince(){
        List<Archipelago>results=archipelagoRepository.findProvince();
        List<ArchipelagoProvinceDTO>sendBack=new ArrayList<>();
        for (Archipelago archipelago:results) {
            ArchipelagoProvinceDTO data=new ArchipelagoProvinceDTO();
            data.setIdProvince(archipelago.getIdProvince());
            data.setProvinceName(archipelago.getProvinceName());
            sendBack.add(data);
        }
        System.out.println(sendBack);
        return sendBack;
    }

    public List<ArchipelagoDistrictDTO>findDistrict(String idCity){
        List<Archipelago>results=archipelagoRepository.findCityOrDistrict(6L,idCity);
        System.out.println(results);
        List<ArchipelagoDistrictDTO>sendBack=new ArrayList<>();
        for(Archipelago archipel: results){
            ArchipelagoDistrictDTO data=new ArchipelagoDistrictDTO();
            data.setIdDistrict(archipel.getIdProvince());
            data.setDistrictName(archipel.getProvinceName());
            sendBack.add(data);
        }
        return sendBack;
    }

    public List<ArchipelagoCityDTO>findCity(String idProvince){
        List<Archipelago>results=archipelagoRepository.findCityOrDistrict(4L,idProvince);
        System.out.println(results);
        List<ArchipelagoCityDTO>sendBack=new ArrayList<>();
        for(Archipelago archipel: results){
            ArchipelagoCityDTO data=new ArchipelagoCityDTO();
            data.setIdCity(archipel.getIdProvince());
            data.setCityName(archipel.getProvinceName());
            sendBack.add(data);
        }
        return sendBack;
    }

}
