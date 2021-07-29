package com.bat.velo.controller;

import com.bat.velo.dto.CreateCategoryDto;
import com.bat.velo.dto.UpdateCategoryDto;
import com.bat.velo.service.ProductCategoryService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/productcategory")
public class ProductCategoryController extends BaseController {

    @Autowired
    protected ProductCategoryService categoryService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ApiOperation("Api for Create Product Category")
    @PutMapping("")
    ResponseEntity createProductcategory(@RequestBody CreateCategoryDto categoryDto) {
        try {
            categoryService.createNewCategory(categoryDto);
            return created();
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Api for Update Product Category")
    @PostMapping("")
    ResponseEntity updateProductcategory(@RequestBody UpdateCategoryDto updateCategory) {
        try {
            return ok(categoryService.updateProdCategoryData(updateCategory));
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Api for Get All Product Categories")
    @GetMapping("/list")
    ResponseEntity getAllProductCategories() {
        try {
            return ok(categoryService.getAllProductCategories());
        } catch (Exception e){
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Api for Delete Product Category")
    @DeleteMapping("")
    public ResponseEntity deleteProductCategory(
            @RequestParam(value = "categoryId", required = true) Integer categoryId) {
        try {
            System.out.println("categoryId --> " + categoryId);

            categoryService.deleteProductCategory(categoryId);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @ApiOperation("Api for Search Product Category")
    @GetMapping("/search")
    public ResponseEntity searchProductCategory(
            @RequestParam(value = "categoryName", required = false) String categoryName) {

        try {
            return ok(categoryService.searchCategoryByName(categoryName));
        } catch (Exception e){
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
}
