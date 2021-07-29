package com.bat.velo.service;

import com.bat.velo.dto.*;
import com.bat.velo.entity.Files;
import com.bat.velo.entity.NewsAndEvent;
import com.bat.velo.entity.ProductCategory;
import com.bat.velo.entity.ProductRedemptionEntity;
import com.bat.velo.repository.FilesRepository;
import com.bat.velo.repository.ProductCategoryRepository;
import com.bat.velo.repository.ProductRedemptionRepository;
import com.bat.velo.repository.RedemptionStatusRepository;
import com.bat.velo.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
public class ProductRedemptionService {

    @Autowired
    ProductRedemptionRepository redemptionRepository;

    @Autowired
    ProductCategoryRepository categoryRepository;

    @Autowired
    protected RedemptionStatusRepository redemptionStatusRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    FilesRepository filesRepository;

    public boolean createNewRedemption(CreateRedemptionDto redemptionDto) throws Exception {

        // check if category exists
        Optional<ProductCategory> pc = categoryRepository.findById(redemptionDto.getCategoryId());
        if (!pc.isPresent()) throw new Exception("Not Found Product Category !");

        // check if redemption exists
        ProductRedemptionEntity redemptionFindByName = redemptionRepository.findRedemptionByName(redemptionDto.getNameRedemption());
        if (redemptionFindByName != null) throw new Exception("Product Redemption has been used !");

        // generate uniq code
        String code = new Date().getDate() + Util.generateNumber();

        ProductRedemptionEntity redemptionEntity = new ProductRedemptionEntity();
        BeanUtils.copyProperties(redemptionDto, redemptionEntity);

        redemptionEntity.setCode(code);
        redemptionRepository.save(redemptionEntity);

        return true;
    }

    public boolean updateRedemptionData(UpdateRedemptionDto updateRedemption) throws Exception {

        Optional<ProductRedemptionEntity> newRedemption = redemptionRepository.findById(updateRedemption.getId());
        if (!newRedemption.isPresent()) throw new Exception("Not Found Product Redemption");

        // check categoryId
        Optional<ProductCategory> pc = categoryRepository.findById(updateRedemption.getCategoryId());
        if (!pc.isPresent()) throw new Exception("Not Found Product Category !");

        newRedemption.get().setNameRedemption(updateRedemption.getNameRedemption());
        newRedemption.get().setDescription(updateRedemption.getDescription());
        newRedemption.get().setRedemPoint(updateRedemption.getRedemPoint());
        newRedemption.get().setStock(updateRedemption.getStock());
        newRedemption.get().setCategoryId(updateRedemption.getCategoryId());
        newRedemption.get().setStatus(updateRedemption.getStatus());

        newRedemption.get().setUpdatedBy(updateRedemption.getUpdatedBy());
        newRedemption.get().setUpdatedDate(updateRedemption.getUpdatedDate());

        redemptionRepository.save(newRedemption.get());

        return true;
    }

    public boolean updateRedemptionByStatus(UpdateRedemptionStatusDto updateRedemptionstatusDto) throws Exception {
        Optional<ProductRedemptionEntity> newRedemption = redemptionRepository.findById(updateRedemptionstatusDto.getId());
        if (!newRedemption.isPresent()) throw new Exception("Not Found Product Redemption");

        newRedemption.get().setStatus(updateRedemptionstatusDto.getStatus());
        newRedemption.get().setUpdatedBy(updateRedemptionstatusDto.getUpdatedBy());
        newRedemption.get().setUpdatedDate(updateRedemptionstatusDto.getUpdatedDate());
        redemptionRepository.save(newRedemption.get());
        return true;
    }

    public void deleteRedemption(int redemptionId) throws Exception {
        redemptionRepository.delete(getProductRedemptionById(redemptionId));
    }

    public ProductRedemptionEntity getProductRedemptionById(long redemptionId) throws Exception {
        Optional<ProductRedemptionEntity> redemptionEntity = redemptionRepository.findById(redemptionId);
        if (!redemptionEntity.isPresent()) throw new Exception("Product Redemption Not Found!");
        return redemptionEntity.get();
    }

//    public boolean createRedemptionStatus(CreateRedemptionStatusDto createRedemptionStatus) throws Exception {
//        Optional<ProductRedemptionEntity> findRedemption = redemptionRepository.findById(createRedemptionStatus.getProductRedemptionId());
//        if (!findRedemption.isPresent()) throw new Exception("Not Found Product Redemption!");
//
//        RedemptionStatusEntity redemptionEntity = new RedemptionStatusEntity();
//        BeanUtils.copyProperties(createRedemptionStatus, redemptionEntity);
//        redemptionStatusRepository.save(redemptionEntity);
//
//        return true;
//    }

    public List<UpdateRedemptionDto> searchRedemption(FilterRedemptionDto request, int pageNum, int pageSize) {
        List<UpdateRedemptionDto> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        String sqlFilter = "";
        String sql = "select id,code,status,SUBSTRING(name_redemption, 1, 100) as name_redemption,description,redem_point,stock,category_id,created_by,created_date,updated_by,updated_date from vlo_product_redemption where ";
        List<String> filters = new ArrayList<>();
        List<String> status = new ArrayList<>();

        if (request.getFilter() != null && !request.getFilter().isEmpty()) {
            List<String> listParams = Arrays.asList(request.getFilter().split(","));
            int no = 0;
            for (String param : listParams) {
                filters.add(" name_redemption LIKE :nameRedemption" + no);
                params.put("nameRedemption" + no, "%" + param + "%");
                no++;
            }
        }

        if (request.getStatus() != null && !request.getStatus().isEmpty()) {
            if (request.getStatus().equals("disable")) {
                status.add(" status = :status ");
                params.put("status", 0);
            } else if (request.getStatus().equals("all")) {

            } else if (request.getStatus().equals("enable")) {
                status.add(" status = :status ");
                params.put("status", 1);
            }
        }

        if (filters.size() != 0) {
            for (int i = 0; i < filters.size(); i++) {
                sqlFilter += filters.get(i);
                if (i != filters.size() - 1) {
                    sqlFilter += " or";
                }
            }
            if (status.size() != 0) sqlFilter += " and ";

        }
        if (status.size() != 0) {
            for (int i = 0; i < status.size(); i++) {
                sqlFilter += status.get(i);
                if (i != status.size() - 1) {
                    sqlFilter += " or";
                }
            }
        }


        String sqls = sql + " "
                + sqlFilter
                + " order by name_redemption asc "
                + ((pageSize > 0) ? " limit " + ((pageNum - 1) * pageSize) + "," + pageSize : "");
        Query query = entityManager.createNativeQuery(sqls, ProductRedemptionEntity.class);

        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }

        List<ProductRedemptionEntity> redemptionEntityList = query.getResultList();

        for (ProductRedemptionEntity redemptionEntity : redemptionEntityList) {
            UpdateRedemptionDto dto = new UpdateRedemptionDto();
            BeanUtils.copyProperties(redemptionEntity, dto);

            result.add(dto);
        }

        return result;
    }


    public void addFile(long prodRedempId, String fileName, String fileData) throws Exception {
        Optional<ProductRedemptionEntity> redemptionEntity = redemptionRepository.findById(prodRedempId);
        if (redemptionEntity.isPresent()) {

            Files file = filesRepository.findByForeignIdAndFileType(redemptionEntity.get().getId(), "productRedemption");

            if (file == null) {

                file = Files.builder()
                        .foreignId(redemptionEntity.get().getId())
                        .fileType("productRedemption")
                        .build();
            }

            file.setFileData(fileData);
            file.setFileName(fileName);

            filesRepository.save(file);
        }
        else
            throw new Exception("User not found");
    }


    public List<Files> getListRedemptionImage(long redemptionId) throws Exception {
        String fileType = "productRedemption";
        List<Files> filesList = new ArrayList<>();

        filesList = filesRepository.findAllByForeignIdAndFileTypes(redemptionId, fileType);
        if (filesList.size() == 0) throw new Exception("File or fileType Not Found!");

        return filesList;
    }
}
