package com.bat.velo.service;

import com.bat.velo.dto.*;
import com.bat.velo.entity.Files;
import com.bat.velo.entity.NewsAndEvent;
import com.bat.velo.entity.ProductCatalogEntity;
import com.bat.velo.entity.ProductCategory;
import com.bat.velo.repository.FilesRepository;
import com.bat.velo.repository.ProductCatalogRepository;
import com.bat.velo.repository.ProductCategoryRepository;
import com.bat.velo.util.Util;
import com.google.gson.JsonObject;
import jdk.nashorn.api.scripting.JSObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class ProductCatalogService {

    @Autowired
    protected ProductCatalogRepository catalogRepository;

    @Autowired
    protected ProductCategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    FilesRepository filesRepository;

    public boolean createNewCatalog(CreateCatalogDto catalogDto) throws Exception {
        Optional<ProductCategory> pc = categoryRepository.findById(catalogDto.getCategoryId());
        if (!pc.isPresent()) throw new Exception("Not Found Product Category !");

        // check if catalog exists
        ProductCatalogEntity catalogFindByName = catalogRepository.findCatalogByName(catalogDto.getNameCatalog());
        if (catalogFindByName != null) throw new Exception("Product catalog has been used");

        // generate uniq code
        String code = new Date().getDate() + Util.generateNumber();

        // save data
        ProductCatalogEntity catalogEntity = new ProductCatalogEntity();
        BeanUtils.copyProperties(catalogDto, catalogEntity);

        catalogEntity.setCodeCatalog(code);
        catalogRepository.save(catalogEntity);

        return true;
    }

    public boolean updateProdCatalogData(UpdateCatalogDto updateCatalogDto) throws Exception {

        Optional<ProductCatalogEntity> newCatalog = catalogRepository.findById(updateCatalogDto.getId());
        if (!newCatalog.isPresent()) throw new Exception("Not Found Product Catalog !");

        newCatalog.get().setCategoryId(updateCatalogDto.getCategoryId());
        newCatalog.get().setNameCatalog(updateCatalogDto.getNameCatalog());
        newCatalog.get().setDescription(updateCatalogDto.getDescription());
        newCatalog.get().setShopUrl(updateCatalogDto.getShopUrl());
        newCatalog.get().setStatus(updateCatalogDto.getStatus());

        newCatalog.get().setUpdatedBy(updateCatalogDto.getUpdatedBy());
        newCatalog.get().setUpdatedDate(updateCatalogDto.getUpdatedDate());

        catalogRepository.save(newCatalog.get());

        return true;
    }

    public void deleteProductCatalog(int catalogId) throws Exception {
        catalogRepository.delete(getProductCatalogById(catalogId));
    }

    public ProductCatalogEntity getProductCatalogById(long catalogId) throws Exception {
        Optional<ProductCatalogEntity> catalogEntity = catalogRepository.findById(catalogId);
        if (!catalogEntity.isPresent()) throw new Exception("Product catalog not found!");
        return catalogEntity.get();
    }

    public boolean updateProdCatalogByStatus(UpdateCatalogStatusDto updateCatalogDto) throws Exception {
        Optional<ProductCatalogEntity> newCatalog = catalogRepository.findById(updateCatalogDto.getId());
        if (!newCatalog.isPresent()) throw new Exception("Not Found Product Catalog!");

        newCatalog.get().setStatus(updateCatalogDto.getStatus());
        newCatalog.get().setUpdatedDate(updateCatalogDto.getUpdatedDate());
        newCatalog.get().setUpdatedBy(updateCatalogDto.getUpdatedBy());

        catalogRepository.save(newCatalog.get());
        return true;
    }

    public List<UpdateCatalogDto> searchCatalog(FilterCatalogDto request, int pageNum, int pageSize) {
        List<UpdateCatalogDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        String sqlFilter = "";

        String sql = "select id,SUBSTRING(name_catalog, 1, 100) as name_catalog,category_id,code,description,shop_url,status,created_by,created_date,updated_by,updated_date from vlo_product_catalog where ";
        List<String> filters = new ArrayList<>();
        List<String> categoryId = new ArrayList<>();

        if (request.getFilter() != null && !request.getFilter().isEmpty()) {
            List<String> listParams = Arrays.asList(request.getFilter().split(","));
            int no = 0;

            for (String param : listParams) {
                filters.add(" name_catalog LIKE :nameCatalog" + no);
                params.put("nameCatalog" + no, "%" + param + "%");
                no++;
            }

            System.out.println("filters >> " + filters);
            System.out.println("params >> " + params);
        }

        if (request.getCategoryId() != null) {
            if(request.getCategoryId()!=0){
                categoryId.add(" category_id = :categoryId");
                params.put("categoryId", request.getCategoryId());
            }
        }

        if(filters.size()!=0){
            for(int i=0;i<filters.size();i++){

                sqlFilter+=filters.get(i);
                if(i!=filters.size()-1){
                    sqlFilter+=" or";
                }
            }
            if(categoryId.size()!=0|| categoryId.size()!=0) sqlFilter+=" and ";
        }

        if(categoryId.size()!=0){
            sqlFilter+= categoryId.get(0);
        }

        String sqls = sql+" "
                + sqlFilter
                + " order by name_catalog asc "
                + ((pageSize > 0) ? " limit " + ((pageNum - 1) * pageSize) + "," + pageSize : "")
               ;
        Query query = entityManager.createNativeQuery(sqls, ProductCatalogEntity.class);
        System.out.println("sqls --> " + sqls);

        for (String key : params.keySet()) {
            query.setParameter(key,params.get(key));
        }

        List<ProductCatalogEntity> productCatalogEntities = query.getResultList();

        for (ProductCatalogEntity catalogEntity : productCatalogEntities) {
            UpdateCatalogDto dto = new UpdateCatalogDto();
            BeanUtils.copyProperties(catalogEntity, dto);
            result.add(dto);
        }

        return result;
    }

    public void addImageFile(long foreignKey, String fileType, String fileName, String fileData) throws Exception {
        Optional<ProductCatalogEntity> catalogEntity = catalogRepository.findById(foreignKey);

        if (!catalogEntity.isPresent()) throw new Exception("Product Catalog not found");

        FilesDTO filesDTO = new FilesDTO();
        Files fileEntity = new Files();
        BeanUtils.copyProperties(filesDTO, fileEntity);

        fileEntity.setFileData(fileData);
        fileEntity.setFileName(fileName);
        fileEntity.setFileType(fileType);
        fileEntity.setForeignId(foreignKey);
        filesRepository.save(fileEntity);
    }

    public FilesDTO getFile(long id, String fileType) throws Exception {
        FilesDTO ret = null;
        Files files = filesRepository.findByIdAndFileType(id, fileType);

        if (files == null) throw new Exception("File or fileType Not Found!");

        ret = new FilesDTO();
        BeanUtils.copyProperties(files, ret);
        return ret;
    }

    public List<Files> getListCatalogImage(long catalogId) throws Exception {
        String fileType = "productCatalog";
        List<Files> filesList = new ArrayList<>();

        filesList = filesRepository.findAllByForeignIdAndFileTypes(catalogId, fileType);
        if (filesList.size() == 0) throw new Exception("File or fileType Not Found!");

        return filesList;
    }

}
