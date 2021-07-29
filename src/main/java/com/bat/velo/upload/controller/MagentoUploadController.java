package com.bat.velo.upload.controller;

import com.bat.velo.controller.BaseController;
import com.bat.velo.util.ResponseMessage;
import com.bat.velo.upload.service.MagentoUploadService;
import com.bat.velo.util.MagentoUploadExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("api/v1/magento")
public class MagentoUploadController extends BaseController {

    @Autowired
    protected MagentoUploadService magentoUploadService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("createdBy") String createdBy) {
        String message = "";

        if (MagentoUploadExcelHelper.hasExcelFormat(file)) {
            try {
                magentoUploadService.saveMagentoUpload(file, createdBy);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
}
