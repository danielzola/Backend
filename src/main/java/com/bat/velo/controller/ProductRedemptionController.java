package com.bat.velo.controller;

import com.bat.velo.dto.*;
import com.bat.velo.service.ProductRedemptionService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@CrossOrigin
@RestController
@RequestMapping("api/v1/productredemption")
public class ProductRedemptionController extends BaseController {

    @Autowired
    ProductRedemptionService redemptionService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("Api for Create Product Redemption")
    @PutMapping("")
    ResponseEntity createRedemption(@RequestBody CreateRedemptionDto createRedemptionDto) {
        try {
            redemptionService.createNewRedemption(createRedemptionDto);
            return created();
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Api for Update Product Redemption")
    @PostMapping("")
    ResponseEntity updateRedemption(@RequestBody UpdateRedemptionDto updateRedemptionDto) {
        try {
            return ok(redemptionService.updateRedemptionData(updateRedemptionDto));
        } catch (Exception x) {
            logger.debug(x.getMessage(), x);
            return badRequest(x.getMessage());
        }
    }

    @ApiOperation("Api for Delete Product Redemption")
    @DeleteMapping("")
    public ResponseEntity deleteRedemption(
            @RequestParam(value = "redemptionId", required = true) Integer redemptionId) {

        try {
            redemptionService.deleteRedemption(redemptionId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Api for Get Product Redemption detail")
    @GetMapping(value = "")
    ResponseEntity detailRedemption(
        @RequestParam(value = "id", required = true) Integer redemptionId) {

        try {
            return ok(redemptionService.getProductRedemptionById(redemptionId));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }


    @ApiOperation("Api for Update Product Redemption By Status - status => disable[0] | enable[1]")
    @PostMapping(value = "/status")
    ResponseEntity updateRedemptionByStatus(@RequestBody UpdateRedemptionStatusDto updateRedemptionstatusDto) {
        try {
            return ok(redemptionService.updateRedemptionByStatus(updateRedemptionstatusDto));
        } catch (Exception x) {
            logger.debug(x.getMessage(), x);
            return badRequest(x.getMessage());
        }
    }

    @ApiOperation("Api for Search Product Redemption - status => disable[0] | enable[1]")
    @PostMapping(value = "/search")
    ResponseEntity searchRedemption(
            @RequestParam(defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(defaultValue = "-1", required = false) Integer pageSize,
            @RequestBody FilterRedemptionDto request) {

        try {
            return ok(redemptionService.searchRedemption(request,pageNum,pageSize));
        } catch (Exception x) {
            logger.debug(x.getMessage(), x);
            return badRequest(x.getMessage());
        }
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("redemptionId") long redemptionId) {

        try {

            String base64 = Base64.getEncoder().encodeToString(file.getBytes());
            redemptionService.addFile(redemptionId, file.getOriginalFilename(), base64);

            return ok();
        }
        catch (Exception e) {

            System.err.println(e.getMessage());
            e.printStackTrace(System.out);
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Api for List Product Catalog Image")
    @GetMapping("/file/list")
    public ResponseEntity listRedemptionImage(
            @RequestParam(value = "redemptionId", required = true) Integer redemptionId) {
        try {
            return ok(redemptionService.getListRedemptionImage(redemptionId));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
