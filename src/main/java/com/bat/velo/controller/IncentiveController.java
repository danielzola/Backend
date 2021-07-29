package com.bat.velo.controller;

import com.bat.velo.service.ArchipelagoServices;
import com.bat.velo.service.IncentiveService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/incentive")
public class IncentiveController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    IncentiveService incentiveService;
    @GetMapping(value = "/contribution",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Api for Find contribution")
    ResponseEntity listIncentive(@RequestParam(name = "userId",required = false) String userId,
                                    @RequestParam(defaultValue = "0", required = false) Integer pageNum,
                                    @RequestParam(defaultValue = "-1", required = false) Integer pageSize){
        try {

            return ok(incentiveService.listContribution(userId, pageNum, pageSize));
        } 
        catch (Exception e) {
            
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping(value = "/history",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Api for Find historyIncentive")
    ResponseEntity listIncentiveHistory(@RequestParam(name = "userId",required = false) String userId,
                                        @RequestParam(defaultValue = "0", required = false) Integer pageNum,
                                        @RequestParam(defaultValue = "-1", required = false) Integer pageSize){
        try {
            
            return ok(incentiveService.findHistory(userId,pageNum,pageSize));
        } 
        catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
