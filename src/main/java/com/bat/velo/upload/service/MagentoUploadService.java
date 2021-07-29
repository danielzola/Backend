package com.bat.velo.upload.service;

import com.bat.velo.entity.*;
import com.bat.velo.repository.UserRepository;
import com.bat.velo.upload.repository.MagentoUploadRespository;
import com.bat.velo.upload.repository.MagentotrxMetadataRepository;
import com.bat.velo.util.MagentoUploadExcelHelper;
import com.bat.velo.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MagentoUploadService {

    @Autowired
    MagentoUploadRespository magentoUploadRepo;

    @Autowired
    MagentotrxMetadataRepository magentotrxMetadataRepo;

    @Autowired
    UserRepository userRepo;

    public void saveMagentoUpload(MultipartFile file, String createdBy) {
        try {
            Date createdDate = new Date();
            String generatedId = createdDate.getSeconds() + Util.generateNumber();

            this.saveMagentoMetadata(file.getOriginalFilename(), createdBy, createdDate, generatedId);

            List<MagentoUploadEntity> magentoUploadEntityList =
                    MagentoUploadExcelHelper.excelMagentoUpload(file.getInputStream(), generatedId);

            magentoUploadRepo.saveAll(magentoUploadEntityList);

         } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public void saveMagentoMetadata(String fileName, String createdBy,
                                    Date createdDate, String generatedId) {
        MagentotrxMetadataEntity metadataEntity = new MagentotrxMetadataEntity();
        metadataEntity.setFileName(fileName);
        metadataEntity.setCreatedDate(createdDate);
        metadataEntity.setUploadBy(createdBy);
        metadataEntity.setGeneratedId(generatedId);

        // System.out.println("metadataEntity >> " + metadataEntity);
        magentotrxMetadataRepo.save(metadataEntity);
    }

    public List<MagentoUploadEntity> findByUserIdAndPagination(
            String userId, Pageable pageable) throws Exception {

        User user = userRepo.findByUserId(userId);
        if (user == null) throw new Exception("User Name Not Found! ");
        String referalCode = user.getReferalCode();

        List<MagentoUploadEntity> magentoUploadEntityList = new ArrayList<>();
        magentoUploadEntityList = magentoUploadRepo.findMagentoBySellerReferralCode(referalCode, pageable);
        return magentoUploadEntityList;
    }
}
