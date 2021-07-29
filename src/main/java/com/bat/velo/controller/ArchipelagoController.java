package com.bat.velo.controller;

import com.bat.velo.dto.CampaignCreate;
import com.bat.velo.dto.CampaignForUpdateDto;
import com.bat.velo.dto.SearchCampaignDto;
import com.bat.velo.dto.UpdateStatusCampaignDto;
import com.bat.velo.service.ArchipelagoServices;
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
@RequestMapping("api/v1/values")
public class ArchipelagoController extends BaseController {

    @Autowired
    protected ArchipelagoServices archipelagoServices;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/province")
    @ApiOperation("Api for Find Province")
    ResponseEntity findProvince() {
        try {
            return ok(archipelagoServices.findProvince());

        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping("/city")
    @ApiOperation("Api for Find City")
    ResponseEntity findCity(@RequestParam("idProvince") String idProvince) {
        try {
            return ok(archipelagoServices.findCity(idProvince));

        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping("/district")
    @ApiOperation("Api for Find District")
    ResponseEntity findDistrict(@RequestParam("idCity") String idCity) {
        try {
            return ok(archipelagoServices.findDistrict(idCity));

        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

}
