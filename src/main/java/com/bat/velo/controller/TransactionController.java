package com.bat.velo.controller;

import com.bat.velo.upload.service.MagentoUploadService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/transaction")
public class TransactionController extends BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MagentoUploadService magentoUploadService;

    @GetMapping(value = "/history")
    @ApiOperation("API For Listing Transaction History")
    ResponseEntity getHistory(@RequestParam(name = "userId", required = true) String userId,
                              Pageable pageable) {
        try {
            return ok(magentoUploadService.findByUserIdAndPagination(userId, pageable));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
