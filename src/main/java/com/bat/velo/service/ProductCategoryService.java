package com.bat.velo.service;

import com.bat.velo.dto.CreateCategoryDto;
import com.bat.velo.dto.UpdateCategoryDto;
import com.bat.velo.entity.ProductCategory;
import com.bat.velo.repository.ProductCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    ProductCategoryRepository categoryRepository;

    public boolean createNewCategory(CreateCategoryDto categoryDto) throws Exception {
        // cek if category exists
        ProductCategory pc = categoryRepository.findByCategoryName(categoryDto.getNameCategory());
        if (pc != null) throw new Exception("Product category has been used");

        // save data
        ProductCategory pcat = new ProductCategory();
        BeanUtils.copyProperties(categoryDto, pcat);

        categoryRepository.save(pcat);
        return true;
    }

    public boolean updateProdCategoryData(UpdateCategoryDto updateCategory) {
        // find by Id
        Optional<ProductCategory> newProductCategory = categoryRepository.findById(updateCategory.getId());

        newProductCategory.get().setNameCategory(updateCategory.getNameCategory());
        newProductCategory.get().setDescription(updateCategory.getDescription());
        newProductCategory.get().setUpdatedBy(updateCategory.getUpdatedBy());
        newProductCategory.get().setUpdatedDate(updateCategory.getUpdatedDate());

        categoryRepository.save(newProductCategory.get());

        return true;
    }

    public List<ProductCategory> getAllProductCategories() {
        List<ProductCategory> productCategoryList = new ArrayList<>();
        categoryRepository.findAll().forEach(productCategoryList::add);
        return productCategoryList;
    }

    public void deleteProductCategory(int categoryId) throws Exception {
        categoryRepository.delete(getProductCategoryById(categoryId));
    }

    public ProductCategory getProductCategoryById(long categoryId) throws Exception {
        Optional<ProductCategory> productCategory = categoryRepository.findById(categoryId);
        if (!productCategory.isPresent()) throw new Exception("Product category not found!");
        return  productCategory.get();
    }

    public List<ProductCategory> searchCategoryByName(String categoryName) throws Exception {
         List<ProductCategory> productCategoryList = new ArrayList<>();
         if (categoryName == null || categoryName.equals("")) throw new Exception("Please enter a category name");

         productCategoryList = categoryRepository.searchByCategoryName(categoryName);
         return productCategoryList;
    }
}
