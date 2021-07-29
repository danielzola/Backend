package com.bat.velo.controller;

import com.bat.velo.dto.FilesDTO;
import com.bat.velo.dto.FilterDto;
import com.bat.velo.dto.NewsActivateDto;
import com.bat.velo.dto.NewsAndEventDTO;
import com.bat.velo.service.NewsService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Base64;

@CrossOrigin
@RestController
@RequestMapping("api/v1/news")
public class NewsController extends BaseController{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    NewsService newsService;

    @PutMapping(value = "")
    @ApiOperation("Api for Create Event Or News")
    ResponseEntity createEventOrNews(@RequestAttribute(name = "userId") @ApiIgnore String userId,
            @RequestBody NewsAndEventDTO request) {
        try {
            return ok(newsService.addNewsOrEvent(request,userId));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
    @PostMapping(value = "")
    @ApiOperation("Api for Update Event or News")
    ResponseEntity updateEventOrNews(@RequestAttribute(name = "userId") @ApiIgnore String userId,
                                     @RequestBody NewsAndEventDTO request){
        try {
            return ok(newsService.updateNewsOrEvent(request,userId));

        }catch (Exception e){
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping(value = "")
    @ApiOperation("Api for get News Or Event")
    ResponseEntity getDataEventOrNews(@RequestParam(name = "newsId") long newsId){
        try{
            return ok(newsService.get(newsId));
        }catch (Exception e){
            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @DeleteMapping(value = "")
    @ApiOperation("For delete news or Event")
    ResponseEntity delete(@RequestAttribute(name = "userId") @ApiIgnore String userId,@RequestParam(name = "newsId") long newsId){
        try{
            return ok(newsService.deleteData(newsId,userId));
        }catch (Exception e){
            logger.debug(e.getMessage(),e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/status")
    @ApiOperation("activate and deactivate news or event")
    ResponseEntity changeStatus(@RequestAttribute(name = "userId") @ApiIgnore String userId,
                                @RequestBody NewsActivateDto request){
        try{
            return ok(newsService.changeActiveDeactive(request,userId));
        }catch (Exception e){
            logger.debug(e.getMessage(),e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/search")
    ResponseEntity searchUser(
            @RequestParam(defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(defaultValue = "-1", required = false) Integer pageSize,
            @RequestBody FilterDto request){

        try {

            return ok(newsService.searchUser(request,pageNum,pageSize));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }


    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("newsId") long newsId) {

        try {

            String base64 = Base64.getEncoder().encodeToString(file.getBytes());
            newsService.addFile(newsId, file.getOriginalFilename(), base64);

            return ok();
        }
        catch (Exception e) {

            System.err.println(e.getMessage());
            e.printStackTrace(System.out);
            return badRequest(e.getMessage());
        }
    }


    @GetMapping("/file")
    ResponseEntity<byte[]> getFile(@RequestParam(name = "newsId") long newsId) throws Exception {

        try {
            FilesDTO file = newsService.getFile(newsId);

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
        }
        catch (Exception e) {

            System.err.println(e.getMessage());
            e.printStackTrace(System.out);
            return new ResponseEntity<>(("File error : " + e.getMessage()).getBytes(), HttpStatus.BAD_REQUEST);
        }
    }
}
