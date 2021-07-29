package com.bat.velo.controller;

import com.bat.velo.dto.*;
import com.bat.velo.service.UserServices;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Base64;

@CrossOrigin
@RestController
@RequestMapping("api/v1/invitation")
public class InvitationController extends BaseController {

    @Autowired
    protected UserServices userService;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "")
    ResponseEntity invitation(@RequestBody InvitationDTO request) {

        try {
            return ok(userService.sendInvitation(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/search")
    ResponseEntity invitationSearch(@RequestBody InvitationDTO request) {

        try {
            return ok(userService.findbyStatus(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }


}


