package com.bat.velo.controller;

import com.bat.velo.dto.RedemptionDto;
import com.bat.velo.dto.SearchUserDto;
import com.bat.velo.service.RedemptionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/redemption")
public class RedemptionController extends BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedemptionService redemptionService;

    @ApiOperation("Api for Create Redemption")
    @PutMapping("")
    ResponseEntity createRedemption(@RequestBody RedemptionDto request){
        try {
            return created(redemptionService.savingRedemption(request));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping(value = "/history")
    ResponseEntity findHistoryRedemp(  @RequestParam(defaultValue = "0", required = false) Integer pageNum,
                                    @RequestParam(defaultValue = "-1", required = false) Integer pageSize,
                                    @RequestParam(required = false) String userId){
        try {

            return ok(redemptionService.findByUserId(userId,pageNum,pageSize));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
