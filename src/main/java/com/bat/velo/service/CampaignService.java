package com.bat.velo.service;

import com.bat.velo.dto.*;
import com.bat.velo.entity.CampaignEntity;
import com.bat.velo.entity.User;
import com.bat.velo.repository.CampaignRepository;
import com.bat.velo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CampaignService {

    @Autowired
    protected CampaignRepository campaignRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    private CampaignEntity campaign;

    public boolean createCampaign(CampaignCreate campaignCreate) throws Exception {
        Optional<User> user = userRepository.findById(Long.valueOf(campaignCreate.getUserId()));
        if (!user.isPresent()) throw new Exception("User name has been used");

        CampaignEntity cm = new CampaignEntity();
        BeanUtils.copyProperties(campaignCreate, cm);

        campaignRepository.save(cm);
        auditService.create("campaign", cm.getCreatedBy(), cm);

        return true;
    }

    public Optional<CampaignEntity> findByCampaignById(String campaignId) {
        return campaignRepository.findById(Long.valueOf(campaignId));
    }

    public CampaignEntity findByCampaignByCampaignId(long campaignId) {
        return campaignRepository.findById(campaignId).orElse(new CampaignEntity());
    }

    public List<CampaignEntity> findAllByUserId(String userId) throws Exception {
        if(userId!=null &&!userId.isEmpty()){
            User user=userRepository.findByUserId(userId);
            List<CampaignEntity> campaignEntities=new ArrayList<>();

            if(user!=null){
                campaignEntities=campaignRepository.findAllByUserId(Integer.valueOf(String.valueOf(user.getId())));
                return campaignEntities;
            }
            throw new Exception("User not found");
        }
            List<CampaignEntity> campaignEntities= (List<CampaignEntity>) campaignRepository.findAll();
        return campaignEntities;
    }

    public boolean updateCampaignData(CampaignForUpdateDto campaignForUpdateDto) throws Exception {
        Optional<User> user = userRepository.findById(Long.valueOf(campaignForUpdateDto.getUserId()));
        if (!user.isPresent()) throw new Exception("User not found");

        Optional<CampaignEntity> campaign = campaignRepository.findById(campaignForUpdateDto.getCampaignId());
        CampaignEntity oldCampaign = new CampaignEntity();

        BeanUtils.copyProperties(campaign.get(), oldCampaign);

        campaign.get().setCampaignName(campaignForUpdateDto.getCampaignName());
        campaign.get().setCampaignDescription(campaignForUpdateDto.getCampaignDescription());
        campaign.get().setStartDate(campaignForUpdateDto.getStartDate());
        campaign.get().setEndDate(campaignForUpdateDto.getEndDate());
        campaign.get().setCampaignStatus(campaignForUpdateDto.getCampaignStatus());
        campaign.get().setPointReward(campaignForUpdateDto.getPointReward());
        campaign.get().setUserId(campaignForUpdateDto.getUserId());
        campaign.get().setUpdatedDate(new Date());
        campaign.get().setCreatedDate(campaignForUpdateDto.getCreatedDate());

        campaignRepository.save(campaign.get());
        auditService.update("campaign", user.get().getUserId(), oldCampaign, campaign.get());

        return true;
    }

    public boolean updateStatusCampaign(UpdateStatusCampaignDto updateStatusCampaignDto) {
        long campaignId = updateStatusCampaignDto.getCampaignId();
        int statusId = updateStatusCampaignDto.getStatusId();

        Optional<CampaignEntity> campaign = campaignRepository.findById(campaignId);
        CampaignEntity oldCampaign = new CampaignEntity();
        BeanUtils.copyProperties(campaign.get(), oldCampaign);

        campaign.get().setCampaignStatus(statusId);
        campaignRepository.save(campaign.get());

        auditService.update("campaign", updateStatusCampaignDto.getCreatedBy(), oldCampaign, campaign.get());

        return true;
    }

    public List<CampaignEntity> searchCampaignByStartdateAndEnddate(SearchCampaignDto request){

        List<CampaignEntity> campaignEntities = new ArrayList<>();

//        if(request.getName() != null && !request.getName().isEmpty()) {
//            System.out.println(request.getName());
//            campaignEntities = campaignRepository.findByCampaignNameByLikes(request.getName());
//            return campaignEntities;
//        }
//
//        if(request.getStartDateFrom()!=null&&!request.getStartDateFrom().toString().isEmpty()){
//            campaignEntities=campaignRepository.findByStartDateeByLikes(request.getStartDateFrom().toPattern());
//            return campaignEntities;
//        }
//
//        if(request.getStartDateTo()!=null&&!request.getStartDateTo().toString().isEmpty()){
//            campaignEntities=campaignRepository.findByEndDateeByLikes(request.getStartDateTo().toPattern());
//            return campaignEntities;
//        }

        campaignEntities =
                campaignRepository.findBy(request.getName(),
                        request.getStartDateFrom().toPattern(),
                        request.getStartDateTo().toPattern());

        return campaignEntities;
    }
}
