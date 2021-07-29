package com.bat.velo.service;

import com.bat.velo.dto.RedempHistDTO;
import com.bat.velo.dto.RedemptionDto;
import com.bat.velo.entity.*;
import com.bat.velo.repository.ProductRedemptionRepository;
import com.bat.velo.repository.RedemptionRepository;
import com.bat.velo.repository.RedemptionStatusRepository;
import com.bat.velo.repository.UserRepository;
import javafx.util.Builder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class RedemptionService {
    @Autowired
    RedemptionRepository redemptionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRedemptionRepository productRedemptionRepository;
    @Autowired
    EntityManager entityManager;
    @Autowired
    AuditService auditService;
    @Autowired
    RedemptionStatusRepository redemptionStatusRepository;

    public Boolean savingRedemption(RedemptionDto request) throws Exception {
        User findUser=userRepository.findByUserId(request.getIdUser());
        if(findUser!=null){
            Optional<ProductRedemptionEntity> findRedempItem=productRedemptionRepository.findById(Long.valueOf(request.getIdProduct()));
            if(findRedempItem.isPresent()){
                ProductRedemptionEntity oldProdRedemp=findRedempItem.get();
                Redemption redempted=redemptionRepository.nativeByIdUserAndIdProduct(request.getIdUser(), Long.valueOf(request.getIdProduct()));
                if(redempted==null){
                    if(findRedempItem.get().getStock()>0){
                        Redemption send=new Redemption();
                        Date date=new Date();
                        BeanUtils.copyProperties(request,send);
                        send.setRedempTime(date);
                        RedemptionStatusEntity redemptionStatusEntity=new RedemptionStatusEntity();
                        redemptionStatusEntity.setStatus(request.getStatus());
                        redemptionStatusEntity.setRedemption(send);
                        send.setProductRedemptionEntity(findRedempItem.get());
                        redemptionStatusRepository.save(redemptionStatusEntity);
                        findRedempItem.get().setStock(findRedempItem.get().getStock()-1);
                        productRedemptionRepository.save(findRedempItem.get());
                        auditService.create("redemption",findUser.getUserId(),send);
                        auditService.update("productRedemption",findUser.getUserId(),oldProdRedemp,findRedempItem.get());
                    }else
                        throw new Exception("insufficient stock !");
                }else
                    throw new Exception("Product Has been Redempt");
            }
            else
               throw new Exception("Product Redempt not found");


            return true;
        }
        else
            throw new Exception("User Not Found");
    }

    public List<RedempHistDTO>findByUserId(String userId, Integer pageNum, Integer pageSize){
        String query="select * from vlo_redemption_status a join vlo_redemption b on a.redemption_id=b.id ";
        HashMap<String,String>params=new HashMap<>();
        if(userId!=null&&!userId.isEmpty()){
            query=query+" Where b.id_user = :userId";
            params.put("userId",userId);
        }
        query+=  " order by b.redemp_time asc "+((pageSize > 0) ? " limit " + ((pageNum - 1) * pageSize) + "," + pageSize : "");
        Query execute = entityManager.createNativeQuery(query, RedemptionStatusEntity.class);
        System.out.println(query);
        for (String key : params.keySet()) {

            execute.setParameter(key,params.get(key));
        }
        List<RedemptionStatusEntity>sendRedemption=execute.getResultList();
        System.out.println(sendRedemption);
        List<RedempHistDTO>list=new ArrayList<>();
        for (RedemptionStatusEntity sends:sendRedemption) {
            RedempHistDTO redempHistDTO= RedempHistDTO.builder().
                    id(sends.getRedemption().getId()).
                    redempTime(sends.getRedemption().getRedempTime()).
                    idProduct(sends.getRedemption().getProductRedemptionEntity().getId()).
                    idUser(sends.getRedemption().getIdUser()).
                    description(sends.getRedemption().getProductRedemptionEntity().getDescription()).
                    status(sends.getStatus()).
                    nameRedemption(sends.getRedemption().getProductRedemptionEntity().getNameRedemption()).
                    redemPoint(sends.getRedemption().getProductRedemptionEntity().getRedemPoint()).
                    redemptQty(1L).build();
            list.add(redempHistDTO);

        }
        return list;
    }
}
