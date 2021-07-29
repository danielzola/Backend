package com.bat.velo.controller;

import com.bat.velo.service.ParameterService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/parameter")
public class ParameterController extends BaseController {

    @Autowired
    protected ParameterService parameterService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("Api for Get Parameter Value")
    @GetMapping(value = "")
    ResponseEntity getParameterByName(@RequestParam(value = "name", required = true) String name) {
        try {
            return ok(parameterService.getParameterByName(name));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
