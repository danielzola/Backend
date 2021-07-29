package com.bat.velo.controller;

import com.bat.velo.dto.*;
import com.bat.velo.service.UserServices;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping("api/v1/user")
public class UserController extends BaseController {

    @Autowired
    protected UserServices userService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/login")
    ResponseEntity login(@RequestBody LoginRqDTO request) {

        try {

            return ok(userService.login(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PutMapping(value = "/igniter")
    @ApiOperation("Api for Create Igniter")
    ResponseEntity createIgniter(
            @RequestAttribute(name = "userId") @ApiIgnore String userId,
            @RequestBody UserDTO request) {
        try {

            request.setRoleCode(3);
            request.setParentReferalCode("");
            request.setCreatedBy(userId);




            return ok(userService.add(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PutMapping(value = "/recruiterAdmin")
    @ApiOperation("Api for Create Recruiter Admin")
    ResponseEntity createRecuriterAdmin(
            @RequestBody UserDTO request) {

        try {

            request.setRoleCode(1);
            request.setParentReferalCode("");
            request.setReferalCode("");



            return ok(userService.add(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PutMapping(value = "/recruiterStaff")
    @ApiOperation("Api for Create Recruiter Staff")
    ResponseEntity createRecuriterStaff(
            @RequestBody UserDTO request) {

        try {

            request.setRoleCode(2);
            request.setParentReferalCode("");
            request.setReferalCode("");



            return ok(userService.add(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PutMapping(value = "/fueler")
    @ApiOperation("Api for Create Fueler")
    ResponseEntity createfueler(@RequestBody UserDTO request){

        try {

            request.setRoleCode(4);
            request.setReferalCode("");
            request.setCreatedBy(request.getUserId());


            return ok(userService.add(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "")
    ResponseEntity updateUser(
                    @RequestAttribute(name = "userId") @ApiIgnore String userId,
                    @RequestBody UserDTO request) {
        System.out.println("masuk COntroller");
        try {

            request.setUpdatedBy(userId);

            return ok(userService.update(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/search")
    ResponseEntity searchUser(
            @RequestParam(defaultValue = "0", required = false) Integer pageNum,
            @RequestParam(defaultValue = "-1", required = false) Integer pageSize,
            @RequestBody SearchUserDto request){

        try {

            return ok(userService.searchUser(request, pageNum, pageSize));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping(value = "")
    ResponseEntity userData(@RequestParam(name = "id", required = true) String id){

        try {

            return ok(userService.get(id));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @GetMapping(value = "/parent")
    ResponseEntity getParent(@RequestParam(name = "userId", required = true) String userId){

        try {

            return ok(userService.getParent(userId));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/status")
    ResponseEntity activeDeactiveUser(@RequestBody ActiveInActiveUserDTO request){

        try {

            return ok(userService.activeInActive(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/igniter/verify")
    @ApiOperation("Set Verified")
    ResponseEntity verifyIgniter(@RequestBody CheckVerifiedDto request) {

        try {

            return ok(userService.verify(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/logout")
    ResponseEntity logout(@RequestBody logoutDTO request){

        try {

            return ok(userService.logout(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/changepassword")
    ResponseEntity changePassword(@RequestBody ChangePasswordRqDTO request){

        try {

            return ok(userService.changePassword(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    // Forgot Password
    // Forgot Password Using put
    @PutMapping(value = "/forgotpassword")
    ResponseEntity forgotPasswordRequest(@RequestBody UserForUpdateIgniterDTO request) {

        try {

            return ok(userService.forgotPasswordRequest(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    // Forgot Password Using GET
    @GetMapping(value = "/forgotpassword")
    ResponseEntity forgotPasswordGet(@RequestParam(name = "token",required = true) String token) {

        try {
            return ok(userService.forgotPasswordGet(token));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    // Forgot Password Using POST
    @PostMapping(value = "/forgotpassword")
    ResponseEntity forgotPasswordVerify(@RequestBody VerifyPassswordDto request) {

        try {

            return ok(userService.forgotPasswordVerify(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
    // End Forgot Password

    //    Change Email Block
//    Change Email PutMapping
    @PutMapping(value = "/changeemail")
    ResponseEntity changeMailRequest(@RequestBody ChangeEmailDto request){

        try {

            return ok(userService.changeEmailRequest(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    //    Change Email GetMapping
    @GetMapping(value = "/changeemail")
    ResponseEntity changeEmailGet(@RequestParam(name = "token",required = true) String token){

        try {

            return ok(userService.changeEmailGet(token));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    //    Change Email PostMapping
    @PostMapping(value = "/changeemail")
    ResponseEntity changeMailVerify(@RequestBody TokenReqDto request){

        try {

            return ok(userService.changeEmailVerify(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
//    End ChangeEmail

    @PostMapping(value = "/otp")
    ResponseEntity Otp(@RequestBody OtpRqDto request){

        try {

            return ok(userService.generateOTP(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }

    @PostMapping(value = "/changephone")
    @ApiOperation("For Change Phone Number")
    ResponseEntity changePhone(@RequestBody ChangePhoneRqDTO request){

        try {

            return ok(userService.changePhone(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }


    @DeleteMapping(value = "/deleteUser")
    @ApiOperation("For delete user")
        ResponseEntity delete(@RequestParam String emailAddress){
            try{
                return ok(userService.deleteData(emailAddress));
            }catch (Exception e){
                logger.debug(e.getMessage(),e);
                return badRequest(e.getMessage());
            }
        }

    @PostMapping(value = "/invite")
    @ApiOperation("Invite via Phone Number")
    ResponseEntity sendSms(@RequestBody InviteViaSmsDTO request){

        try {

            return ok(userService.sendSms(request));
        }
        catch (Exception e) {

            logger.debug(e.getMessage(), e);
            return badRequest(e.getMessage());
        }
    }
    
    @PostMapping("/bulkigniter")
    public ResponseEntity<?> bulkIgniter(
                                            @RequestAttribute(name = "userId") @ApiIgnore String userUpdate,
                                            @RequestParam("file") MultipartFile file) {

        try {
         
            System.out.println("sini : " + file.getOriginalFilename());
            
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            
            List<String> errorList = new ArrayList<>();
            int successCount = 0;
                
                
            for(Sheet sheet: wb) {
                
                Iterator<Row> rowIterator = sheet.rowIterator();
                boolean header = true; 
                
                while (rowIterator.hasNext()) {
                    
                    Row row = rowIterator.next();
                    
                    if (header)
                        header = false;
                    else {

                        String refCode = String.valueOf(row.getCell(0));
                        String userId = String.valueOf(row.getCell(2));

                        try {
                            userService.promoteIgniter(refCode, userId, userUpdate);
                            successCount++;
                        }
                        catch (Exception e) {

                            errorList.add(refCode + " : " + e.getMessage());
                        }
                    } 
                }
            }
            
            UploadBulkIgniterRq ret = new UploadBulkIgniterRq();
            ret.setSucess(successCount);
            ret.setFail(errorList.size());
            ret.setFailList(errorList);
            
            return ok(ret);
        }
        catch (Exception e) {
            
            System.err.println(e.getMessage());
            e.printStackTrace(System.out);
            return badRequest(e.getMessage());
        }
    }
    
    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("userId") String userId,
                                        @RequestParam("fileType") String fileType) {

        try {

            String base64 = Base64.getEncoder().encodeToString(file.getBytes()); 
            userService.addFile(userId, fileType, file.getOriginalFilename(), base64);
            
            return ok();
        }
        catch (Exception e) {
            
            System.err.println(e.getMessage());
            e.printStackTrace(System.out);
            return badRequest(e.getMessage());
        }
    }
    
    @PostMapping("/filebase64")
    public ResponseEntity<?> addFile(@RequestBody UserFileRq dto) {

        try {

            userService.addFile(dto.getUserId(), dto.getFileType(), dto.getFileName(), dto.getFileData());
            
            return ok();
        }
        catch (Exception e) {
            
            System.err.println(e.getMessage());
            e.printStackTrace(System.out);
            return badRequest(e.getMessage());
        }
    }
    
    @GetMapping("/file")
    ResponseEntity<byte[]> getFile(
                                @RequestParam(name = "userId") String userName,
                                @RequestParam(name = "fileType") String fileType) throws Exception {
        
        try {
            UserFileDTO file = userService.getFile(userName, fileType);
            
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


