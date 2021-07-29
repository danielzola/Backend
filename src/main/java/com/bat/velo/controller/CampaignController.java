package com.bat.velo.controller;

import com.bat.velo.dto.CampaignCreate;
import com.bat.velo.dto.CampaignForUpdateDto;
import com.bat.velo.dto.SearchCampaignDto;
import com.bat.velo.dto.UpdateStatusCampaignDto;
import com.bat.velo.entity.User;
import com.bat.velo.repository.UserRepository;
import com.bat.velo.service.CampaignService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("api/v1/campaign")
public class CampaignController extends BaseController {

    @Autowired
    protected CampaignService campaignService;

    @Autowired
    protected UserRepository userRepo;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("")
    @ApiOperation("Api for Create Campaign")
    ResponseEntity createCampaign(@RequestBody CampaignCreate request) {
        try {

            User user = userRepo.findById(Long.valueOf(request.getUserId())).orElse(new User());
            request.setCreatedBy(user.getUserId());

            campaignService.createCampaign(request);
            return created();

        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Api for Find Campaign By ID")
    ResponseEntity findCampaignById(@RequestParam(name = "idCampaign",required = true) long idCampaign){
        try {
            return ok(campaignService.findByCampaignByCampaignId(idCampaign));

        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping(value = "/all",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Api for Find Campaign By UserId ID")
    ResponseEntity findCampaignByUserId(@RequestParam(name = "userId",required = false) String userId){
        try {
            return ok(campaignService.findAllByUserId(userId));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }


    @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Api for Update Campaign")
    ResponseEntity updateCampaign(@RequestBody CampaignForUpdateDto campaignForUpdateDto) {
        try {
            return ok(campaignService.updateCampaignData(campaignForUpdateDto));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PutMapping(value = "/status",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Api for Update Status Campaign")
    ResponseEntity updateCampaignByStatus(@RequestBody UpdateStatusCampaignDto updateStatusCampaignDto) {
        try {
            return ok(campaignService.updateStatusCampaign(updateStatusCampaignDto));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/search",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Api for Search Campaign")
    ResponseEntity searchCampaign(@RequestBody SearchCampaignDto requestData) {
        try {
            return ok(campaignService.searchCampaignByStartdateAndEnddate(requestData));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

}
