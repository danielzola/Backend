package com.bat.velo.controller;

import com.bat.velo.dto.DashboardDataRq;
import com.bat.velo.service.DashboardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("api/v1/dashboard")
public class DashboardController extends BaseController {

    @Autowired
    protected DashboardService dashService;
    
    @Autowired
    protected ObjectMapper objMapper;
    
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @PostMapping(value = "/data")
    ResponseEntity getData(@RequestBody DashboardDataRq request) {

        try {

            return ok(dashService.getData(request.getName(), request.getParams()));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
    
    @GetMapping(value = "/xls")
    ResponseEntity getXLS(@RequestParam(name = "filter") String filterString) {

        try {
            
            DashboardDataRq filter = objMapper.readValue(filterString, DashboardDataRq.class);
                
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            System.out.println(objMapper.writeValueAsString(filter));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            headers.add("Content-Disposition", "attachment; filename=" + filter.getName() + "_" + ts + ".xlsx");

            return new ResponseEntity<>(dashService.getXLS(filter.getName(), filter.getParams()), 
                                                headers,
                                                HttpStatus.OK);
        }
        catch (Exception e) {
         
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
    
    @PostMapping(value = "/dto")
    ResponseEntity getDto(@RequestBody DashboardDataRq request) {

        try {

            return ok(dashService.getDtoList(request.getName(), request.getParams()));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
