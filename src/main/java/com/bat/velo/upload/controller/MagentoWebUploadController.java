package com.bat.velo.upload.controller;

import com.bat.velo.upload.service.MagentoUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("v1/magento")
public class MagentoWebUploadController {

    @Autowired
    protected MagentoUploadService magentoUploadService;

    @GetMapping("/")
    public String index() {
        return "multipartfile/uploadform.html";
    }

    @PostMapping("/")
    public String uploadMultipartFile(@RequestParam("file") MultipartFile file,
                                      @RequestParam(value = "createdBy", required = false) String createdBy,
                                      Model model) {
        try {
            // fileServices.store(file);
            magentoUploadService.saveMagentoUpload(file, createdBy);
            model.addAttribute("message", "File uploaded successfully!");
        } catch (Exception e) {
            model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
        }
        return "multipartfile/uploadform.html";
    }
}
