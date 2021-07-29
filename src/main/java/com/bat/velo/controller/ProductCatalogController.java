package com.bat.velo.controller;

import com.bat.velo.dto.*;
import com.bat.velo.entity.Files;
import com.bat.velo.service.ProductCatalogService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@CrossOrigin
@RestController
@RequestMapping("api/v1/catalog")
public class ProductCatalogController extends BaseController {

    @Autowired
    protected ProductCatalogService catalogService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

     @ApiOperation("Api for Create Product Catalog")
     @PutMapping("")
     ResponseEntity createProductCatalog(@RequestBody CreateCatalogDto catalogDto) {
         try {
             catalogService.createNewCatalog(catalogDto);
             return created();
         } catch (Exception e) {
             logger.debug(e.getMessage(), e);
             return badRequest(e.getMessage());
         }
     }

    @ApiOperation("Api for Update Product Catalog")
    @PostMapping("")
    ResponseEntity updateProductCatalog(@RequestBody UpdateCatalogDto updateCatalogDto) {
        try {
            return ok(catalogService.updateProdCatalogData(updateCatalogDto));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Api for Delete Product Catalog")
    @DeleteMapping("")
    public ResponseEntity deleteCatalog(
            @RequestParam(value = "catalogId", required = true) Integer catalogId) {
         try {
             catalogService.deleteProductCatalog(catalogId);
             return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
         } catch (Exception e) {
             logger.debug(e.getMessage(), e);
             return badRequest(e.getMessage());
         }
    }

    @ApiOperation("Api for Get Product Catalog detail")
    @GetMapping(value = "")
    ResponseEntity detailCatalog(
            @RequestParam(value = "catalogId", required = true) Integer catalogId) {
         try {
             return ok(catalogService.getProductCatalogById(catalogId));
         } catch (Exception e) {
             logger.debug(e.getMessage(), e);
             return badRequest(e.getMessage());
         }
    }

    @ApiOperation("Api for Update Product Catalog By Status => Acivate[0] | Deactivate[1]")
    @PostMapping(value = "/status")
    ResponseEntity updateCatalogByStatus(@RequestBody UpdateCatalogStatusDto catalogDto) {
         try {
             return ok(catalogService.updateProdCatalogByStatus(catalogDto));
         } catch (Exception e) {
             logger.debug(e.getMessage(), e);
             return badRequest(e.getMessage());
         }
    }

    @ApiOperation("Api for Search Product Catalog")
    @PostMapping(value = "/search")
    ResponseEntity searchCatalog(
            @RequestParam(defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(defaultValue = "-1", required = false) Integer pageSize,
            @RequestBody FilterCatalogDto request) {

         try {
             return ok(catalogService.searchCatalog(request,pageNum,pageSize));
         } catch (Exception e) {
             logger.debug(e.getMessage(), e);
             return badRequest(e.getMessage());
         }
    }

    @ApiOperation("Api for Upload Image Product Catalog")
    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("foreignId") long foreignId) {
            // @RequestParam("fileType") String fileType
         try {

             String fileType = "productCatalog";
             String base64 = Base64.getEncoder().encodeToString(file.getBytes());

             catalogService.addImageFile(foreignId, fileType, file.getOriginalFilename(), base64);

             return ok();

         } catch (Exception e) {
             System.err.println("err upload message: " + e.getMessage());
             e.printStackTrace(System.out);
             return badRequest(e.getMessage());
         }
    }

    @ApiOperation("Api for Get event Image")
    @GetMapping("/file")
    ResponseEntity<byte[]> getFile(
            @RequestParam(name = "id") long id) throws Exception {
        try {

            String fileType = "productCatalog";
            FilesDTO file = catalogService.getFile(id, fileType);

            String targetType = "application/octet-stream";
            String fileName = file.getFileName().toLowerCase();

            if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"))
                targetType = "image/jpeg";
            else if (fileName.endsWith(".png"))
                targetType = "image/png";
            else if (fileName.endsWith(".gif"))
                targetType = "image/gif";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", targetType);

            return new ResponseEntity<>(Base64.getDecoder().decode(file.getFileData()),
                    headers,
                    HttpStatus.OK);

        } catch (Exception e) {
            System.err.println("err message: " + e.getMessage());
            e.printStackTrace(System.out);
            return new ResponseEntity<>(("File error : " + e.getMessage()).getBytes(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation("Api for List Product Catalog Image")
    @GetMapping("/file/list")
    public ResponseEntity listCatalogImage(
            @RequestParam(value = "catalogId", required = true) Integer catalogId) {
         try {
             return ok(catalogService.getListCatalogImage(catalogId));
         } catch (Exception e) {
             logger.debug(e.getMessage(), e);
             return badRequest(e.getMessage());
         }
    }

}
