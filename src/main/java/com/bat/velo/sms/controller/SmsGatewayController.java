package com.bat.velo.sms.controller;

import com.bat.velo.controller.BaseController;
import com.bat.velo.sms.service.SmsGatewayService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@CrossOrigin
@RestController
@RequestMapping("api/v1/sms")
public class SmsGatewayController extends BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected SmsGatewayService smsGatewayService;

    @GetMapping("getSmsToken")
    @ApiOperation("Api for Get SMS Token")
    public ResponseEntity getSmsToken() {
        try {
            return ok(smsGatewayService.getSmsToken());
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping("sendSmsToClient")
    @ApiOperation("Api for Send SMS")
    public ResponseEntity sendSms(@RequestParam(name = "accessToken", required = true) String accessToken) {
        try {
            HashMap<String,String> data=new HashMap<>();
            data.put("sender","087878144347");
            data.put("recipient","083871772169");
            data.put("message","pesan message");
            return ok(smsGatewayService.sendSmsToClient(accessToken,data));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
