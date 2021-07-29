package com.bat.velo.service;

import com.bat.velo.dto.DashboardDataDTO;
import com.bat.velo.dto.DashboardSerieDTO;
import com.bat.velo.dto.IncentiveContibutionDTO;
import com.bat.velo.dto.IncentiveDto;
import com.bat.velo.entity.DashboardItem;
import com.bat.velo.entity.Incentive;
import com.bat.velo.entity.Redemption;
import com.bat.velo.entity.User;
import com.bat.velo.repository.DashboardItemRepository;
import com.bat.velo.repository.IncentiveRepository;
import com.bat.velo.repository.UserRepository;
import com.bat.velo.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import org.hibernate.SQLQuery;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;

@Service
public class IncentiveService {
    
    @Autowired
    protected DashboardItemRepository itemRepo;
    
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IncentiveRepository incentiveRepository;
    private List<Incentive> datas;

    public List<IncentiveDto> findIncentiveByUserId(String userId) throws Exception {
        
        List<IncentiveDto> returner = new ArrayList<>();
        if(userId!=null&&!userId.isEmpty()){
            User foundUser=userRepository.findByUserId(userId);
            if(foundUser!=null){
                datas = incentiveRepository.findAllByUserId(Long.valueOf(foundUser.getId()));
                for (Incentive data: datas) {
                    IncentiveDto incentiveDto=new IncentiveDto();
                    BeanUtils.copyProperties(data,incentiveDto);
                    incentiveDto.setUserId(foundUser.getUserId());
                    returner.add(incentiveDto);
                }
                return returner;
            }else
                throw new Exception("user not found");
        }
        datas= (List<Incentive>) incentiveRepository.findAll();
        System.out.println("data :"+datas);
        if(datas.size()>0){
            for (Incentive data: datas) {
                IncentiveDto incentiveDto=new IncentiveDto();
                BeanUtils.copyProperties(data,incentiveDto);
                Optional<User> foundUser=userRepository.findById(data.getUserId());
                if(foundUser.isPresent()){
                    incentiveDto.setUserId(foundUser.get().getUserId());
                }
                returner.add(incentiveDto);
            }
            return returner;
        }
        throw new Exception("empty data");
    }

    public List<IncentiveContibutionDTO> listContribution(String userId, int pageNum, int pageSize) throws Exception {
        
        HashMap<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        
        String sql = "select\n" +
                        "	usr.email_address as sourceUserName,\n" +
                        "	usr.name as sourceName,\n" +
                        "	src.total as totalAmount\n" +
                        "from\n" +
                        "(\n" +
                        "	select\n" +
                        "		source_user_id,\n" +
                        "		cast(sum(amount) as signed) as total\n" +
                        "	from\n" +
                        "		vlo_incentive inc\n" +
                        "	where inc.user_id = (select id from vlo_user where email_address = :userId limit 1) \n" +
                        "	group by source_user_id\n" +
                        ") src\n" +
                        "left join vlo_user usr on usr.id = src.source_user_id "
                + "order by usr.name asc ";
        
        if (pageSize > 0)
            sql +=  " limit " + ((pageNum - 1) * pageSize) + "," + pageSize;
        
        SQLQuery query = entityManager.createNativeQuery(sql).unwrap(NativeQuery.class);
        
        for (String key : params.keySet()) {

            query.setParameter(key, params.get(key));
        }

        query.setResultTransformer(Transformers.aliasToBean(IncentiveContibutionDTO.class));

        List<IncentiveContibutionDTO> result = query.getResultList();
                
        return result;
    }
    
    public List<IncentiveDto> findHistory(String userId, int pageNum, int pageSize) throws Exception {
        
        List<IncentiveDto> returner = new ArrayList<>();

        String query="select * from vlo_incentive ";
        
        HashMap<String,String> params = new HashMap<>();
        
        if (userId != null && !userId.isEmpty()) {
            
            User foundUser = userRepository.findByUserId(userId);
            
            if (foundUser!=null){
                
                query = query + " where user_id = :userId";
                params.put("userId", String.valueOf(foundUser.getId()));
            }
            else
                throw new Exception("User not found");
        }
        
        query += " order by create_time desc " +
                ((pageSize > 0) ? " limit " + ((pageNum - 1) * pageSize) + "," + pageSize : "");
        
        Query execute = entityManager.createNativeQuery(query, Incentive.class);
        
        System.out.println(query);
        
        for (String key : params.keySet()) {

            execute.setParameter(key,params.get(key));
        }
        
        datas = execute.getResultList();
        
        if (datas.size() > 0) {
            
            for (Incentive data : datas) {
                
                IncentiveDto incentiveDto = new IncentiveDto();
                BeanUtils.copyProperties(data,incentiveDto);
                
                Optional<User> foundUser = userRepository.findById(data.getSourceUserId());
                
                if (foundUser.isPresent()){
                    
                    incentiveDto.setSourceUserId(foundUser.get().getUserId());
                    incentiveDto.setSourceUserName(foundUser.get().getName());
                }
                
                if (incentiveDto.getStatus() == 0)
                    incentiveDto.setStatusName("Menunggu Pengiriman");
                else
                    incentiveDto.setStatusName("Terkirim");
                
                returner.add(incentiveDto);
            }
        }
        
        return returner;
    }
}
