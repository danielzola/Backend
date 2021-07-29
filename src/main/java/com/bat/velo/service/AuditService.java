package com.bat.velo.service;

import com.bat.velo.dto.AuditDTO;
import com.bat.velo.entity.AuditLog;
import com.bat.velo.entity.User;
import com.bat.velo.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class AuditService {
    
    @Autowired
    protected ObjectMapper objMapper;
    
    @Autowired
    protected AuditLogRepository auditRepo;

    @Autowired
    private EntityManager entityManager;


    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public void create(String category, String userId, Object current) {
        
        log(category, "create", userId, null, current);
    }
    
    public void update(String category, String userId, Object prev, Object current) {
        
        log(category, "update", userId, prev, current);
    }
    
    public void delete(String category, String userId, Object prev) {
        
        log(category, "delete", userId, prev, null);
    }
    
    protected void log(String category, String auditType, String userId, Object prev, Object current) {
        
        try {
            auditRepo.save(
                    AuditLog.builder()
                    .auditTime(new Date())
                    .auditType(auditType)
                    .category(category)
                    .current(current == null ? null : objMapper.writeValueAsString(current))
                    .prev(prev == null ? null : objMapper.writeValueAsString(prev))
                    .userId(userId)
                    .build());
        }
        catch (Exception e) {
         
            logger.debug("Error write audit log : " + e.getMessage(), e);
        }
    }


    public List<AuditLog>findAudit(AuditDTO request) throws Exception {
        Map<String, Object> params = new HashMap<>();
        String sqlFilter = "select * from vlo_auditlog where";
        List<String>value=new ArrayList<>();
        System.out.println("atas");
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        System.out.println(request);
        String dateFrom,dateTo;
        try{

            if(request.getAuditFrom()!=null &&!request.getAuditFrom().toString().isEmpty()){
                dateFrom=simpleDateFormat.format(request.getAuditFrom());
                value.add(" audit_time >= :dateFrom");
                params.put("dateFrom", dateFrom);
            }
            if(request.getAuditTo()!=null &&!request.getAuditTo().toString().isEmpty()){
                dateTo=simpleDateFormat.format(request.getAuditTo());
                value.add(" audit_time <= :dateTo");
                params.put("dateTo", dateTo);
            }

            if(request.getAuditType()!=null &&!request.getAuditType().isEmpty()){
                value.add(" audit_type = :auditType");
                params.put("auditType", request.getAuditType());
            }

            if(request.getCategory()!=null &&!request.getCategory().isEmpty()){
                value.add(" category = :category");
                params.put("category", request.getCategory());

            }

            if(request.getUserId()!=null &&!request.getUserId().isEmpty()){
                value.add(" user_id = :userId");
                params.put("userId", request.getUserId());

            }
            for(int i=0;i<value.size();i++){
                sqlFilter+=value.get(i);
                if(i!=value.size()-1){
                    sqlFilter+=" and";
                }
            }
            Query query = entityManager.createNativeQuery(sqlFilter, AuditLog.class);
            for (String key : params.keySet()) {
                System.out.println(key);

                query.setParameter(key, params.get(key));
            }
//            System.out.println(query.getParameter(1));
            List<AuditLog> auditLogs = query.getResultList();

            return auditLogs;
        }catch (Exception e){
            throw new Exception("failed") ;
        }
    }
}
