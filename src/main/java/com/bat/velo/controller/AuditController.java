package com.bat.velo.controller;

import com.bat.velo.dto.AuditDTO;
import com.bat.velo.repository.AuditLogRepository;
import com.bat.velo.service.AuditService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/auditlog")
public class AuditController extends BaseController{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuditService auditService;

    @PostMapping(value = "/search",produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Api for Find Audit Log")
    ResponseEntity findCampaignById(@RequestBody AuditDTO auditDTO){
        try {
            return ok(auditService.findAudit(auditDTO));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
