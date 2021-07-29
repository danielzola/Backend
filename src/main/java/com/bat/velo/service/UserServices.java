package com.bat.velo.service;

import com.bat.velo.dto.*;
import com.bat.velo.entity.*;
import com.bat.velo.mail.service.EmailService;
import com.bat.velo.repository.*;
import com.bat.velo.sms.service.SmsGatewayService;
import com.bat.velo.util.Util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class UserServices {

    @Autowired
    protected UserRepository userRepo;

    @Autowired
    TokenizationRepository tokenizationRepository;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private EmailService emailService;

    @Autowired
    private AuditService auditService;

    @Autowired
    protected MessagePoolService msgService;
    
    @Autowired
    private UserFileRepository fileRepo;
    @Autowired
    private TierRepository tierRepository;
    @Autowired
    private SmsGatewayService smsGatewayService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${otp.expired}")
    protected int otpExpired;

    @Value("${email.expired}")
    protected int emailExpired;
    private String referalCode;
    String pattern = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public LoginRsDTO login(LoginRqDTO loginRq) throws Exception {

        User user = userRepo.findByUserId(loginRq.getUserId());

        if (user != null) {

            if (encoder.matches(loginRq.getPass(), user.getPass())) {


                String accessToken = jwtService.generateToken(loginRq.getUserId());

                UserDTO userDTO = new UserDTO();

                BeanUtils.copyProperties(user, userDTO);
                try{
                    Optional<Tier> tier= tierRepository.findById(Long.valueOf(user.getTierId()));
                    userDTO.setTierName(tier.get().getTierName());
                }catch (Exception e){
                    
                }
                userDTO.setPass("");
                userDTO.setIdCardImage("");

//                emailService.mapperMails();
                return LoginRsDTO.builder()
                        .accessToken(accessToken)
                        .user(userDTO)
                        .build();
            }
            else
                throw new Exception("Invalid Username or Password");
        }
        else
            throw new Exception("Invalid Username or Password");
    }

    public String add(UserDTO request) throws Exception {

        User user = userRepo.findByUserId(request.getUserId());

        if (user != null)
            throw new Exception("User name has been used");

        if (Util.calculateAge(request.getDateOfBirth()))
            throw new Exception("Age must older than 18");

        if(!Util.isValidEmail(request.getUserId()))
            throw new Exception("Email Format Not Valid");

        switch (request.getRoleCode()) {
            case 1:
                request.setIsActive(1);
                request.setIsVerified(1);
                break;
            case 2:
                request.setIsActive(1);
                request.setIsVerified(1);
                break;
            case 3:
                referalCode = Util.generateReferal();
                request.setReferalCode(cekValidateReferalCode(referalCode));
                request.setIsVerified(0);
                break;
            case 4:
                referalCode=Util.generateReferal();
                request.setReferalCode(cekValidateReferalCode(referalCode));
                request.setIsVerified(1);
                User temp = userRepo.findIsValidByReferal(request.getParentReferalCode());

                if (temp == null)
                    throw new Exception("Igniter not valid");
                break;
        }

        User newUser = new User();
        BeanUtils.copyProperties(request, newUser);
        newUser.setPass(encoder.encode(request.getPass()));
        newUser.setIsActive(1);
        newUser.setCreatedDate(new Date());
        userRepo.save(newUser);

        auditService.create("user", newUser.getCreatedBy(), newUser);
        if(newUser.getRoleCode()==3){
            List<String>destination=new ArrayList<>();
            destination.add(request.getUserId());
            destination.add(request.getName());
            Optional<EmailTemplate> emailTemplate=templateRepository.findById(1L);
            String partBody=emailTemplate.get().getHtmlTags();
            System.out.println(partBody);

            partBody=partBody.replaceAll("EmailAddress",request.getUserId());
            partBody=partBody.replaceAll("TemporaryPassword",request.getPass());
            partBody=partBody.replaceAll("ReferralCode",referalCode);
            emailService.mapperMails(destination,partBody);
            System.out.println(partBody);

        }

        // to do : send email if add igniter

        return referalCode;
    }

    public boolean update(UserDTO request) throws Exception {
        System.out.println("di service");
        User user = userRepo.findByUserId(request.getUserId());
        if (user == null)
            throw new Exception("User not found");

        if (Util.calculateAge(request.getDateOfBirth()))
            throw new Exception("Age must older than 18");

        User updatedUser = new User();
        BeanUtils.copyProperties(request, updatedUser);

        if ((request.getPass() == null) || request.getPass().isEmpty())
            updatedUser.setPass(user.getPass());
        else
            updatedUser.setPass(encoder.encode(request.getPass()));
        
        // copy unchange value
        updatedUser.setId(user.getId());
        updatedUser.setIsVerified(user.getIsVerified());
        updatedUser.setIsActive(user.getIsActive());
        updatedUser.setToken(user.getToken());
        updatedUser.setRoleCode(user.getRoleCode());
        updatedUser.setUpdatedDate(new Date());

        userRepo.save(updatedUser);
        
        auditService.update("user", request.getUpdatedBy(), user, updatedUser);
        if(updatedUser.getRoleCode()==4||updatedUser.getRoleCode()==3){
            List<String>destination=new ArrayList<>();
            destination.add(request.getUserId());
            destination.add(request.getName());
            Optional<EmailTemplate> emailTemplate=templateRepository.findById(updatedUser.getRoleCode()==4?2L:5L);
            String partBody=emailTemplate.get().getHtmlTags();
            System.out.println(partBody);

            partBody=partBody.replaceAll("Name",updatedUser.getName());
            partBody=partBody.replaceAll("DateTime",simpleDateFormat.format(updatedUser.getUpdatedDate()));
            partBody=partBody.replaceAll("Admin",updatedUser.getCreatedBy());
            emailService.mapperMails(destination,partBody);
            System.out.println(partBody);

        }

        return true;
    }

    public UserDTO get(String userId) {
        User user = userRepo.findByUserId(userId);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        try{
            Optional<Tier> tier= tierRepository.findById(Long.valueOf(user.getTierId()));
            userDTO.setTierName(tier.get().getTierName());

        }catch (Exception e){

        }

        userDTO.setPass("");
        userDTO.setIdCardImage("");

        return userDTO;
    }

    public UserDTO getParent(String userId) throws Exception {

        User user = userRepo.findByUserId(userId);

        if(user!=null){
            User parent=userRepo.findByReferalCode(user.getParentReferalCode());

            UserDTO userDTO = new UserDTO();


            BeanUtils.copyProperties(parent, userDTO);
            try{
                Optional<Tier> tier= tierRepository.findById(Long.valueOf(user.getTierId()));
                userDTO.setTierName(tier.get().getTierName());

            }catch (Exception e){

            }

            userDTO.setPass("");
            userDTO.setIdCardImage("");

            return userDTO;
        }else
            throw new Exception("User Not Found");

    }

    public List<UserDTO> searchUser(SearchUserDto request, int pageNum, int pageSize) {

        List<UserDTO> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        String sqlFilter = "";
        String sql = "select * from vlo_user where";
        List<String>valueCreated=new ArrayList<>();
        List<String>valueRoles=new ArrayList<>();
        List<String>valueFilter=new ArrayList<>();
        List<String>valueParentReferalCode=new ArrayList<>();
        List<String>valueIsActive=new ArrayList<>();
        List<String>valueIsVerified=new ArrayList<>();
        int sizeList=0;
        int oldSize=0;
        if (request.getCreatedBy() != null && !request.getCreatedBy().isEmpty()) {

            List<String> listParams = Arrays.asList(request.getCreatedBy().split(","));

            int no = 0;
            for (String param: listParams) {
                    valueCreated.add(" created_by like :create" + no);
                    params.put("create" + no, "%"+param+"%");
                no++;

            }
            System.out.println("end In Create");
        }
        if (request.getRoleCode() != null && !request.getRoleCode().isEmpty()) {

            List<String> listParams = Arrays.asList(request.getRoleCode().split(","));

            int no = 0;
            for (String param: listParams) {

                valueRoles.add(" role_code like :role" + no);
                    params.put("role" + no, "%"+param+"%");

                no++;

            }

        }

        if (request.getFilter()!= null && !request.getFilter().isEmpty()) {


            List<String> listParams = Arrays.asList(request.getFilter().split(","));

            int no = 0;
            for (String param: listParams) {
                valueFilter.add(" name like :name" + no );
                    params.put("name"+no,"%"+param+"%");
                valueFilter.add( " email_address like :email" + no);
                    params.put("email"+no,"%"+param+"%");


                no++;
            }
        }
        if(request.getParentReferalCode()!=null&&!request.getParentReferalCode().isEmpty()){
            valueParentReferalCode.add(" parent_referal_code =:parentReferalCode ");
            params.put("parentReferalCode",request.getParentReferalCode());
        }
        if(request.getIsActive()!=null){
            valueIsActive.add(" is_active = :isActive ");
            params.put("isActive",request.getIsActive());
        }
        if(request.getIsVerified()!=null){
            valueIsVerified.add(" is_verified = :isVerified ");
            params.put("isVerified",request.getIsVerified());
        }
        if(valueCreated.size()!=0){
            for(int i=0;i<valueCreated.size();i++){

                sqlFilter+=valueCreated.get(i);
                if(i!=valueCreated.size()-1){
                    sqlFilter+=" or";
                }
            }
            if(valueRoles.size()!=0
                ||valueFilter.size()!=0
                ||valueParentReferalCode.size()!=0
                ||valueIsActive.size()!=0
                ||valueIsVerified.size()!=0)
            sqlFilter+=" and ";

        }
        if(valueRoles.size()!=0){
            for(int i=0;i<valueRoles.size();i++){

                sqlFilter+=valueRoles.get(i);
                if(i!=valueRoles.size()-1){
                    sqlFilter+=" or";
                }
            }
            if(valueFilter.size()!=0
                ||valueParentReferalCode.size()!=0
                ||valueIsActive.size()!=0
                ||valueIsVerified.size()!=0)
                sqlFilter+=" and ";

        }

        if(valueFilter.size()!=0){
            for(int i=0;i<valueFilter.size();i++){

                sqlFilter+=valueFilter.get(i);
                if(i!=valueFilter.size()-1){
                    sqlFilter+=" or";
                }
            }
            if(valueParentReferalCode.size()!=0
                ||valueIsActive.size()!=0
                ||valueIsVerified.size()!=0)
                sqlFilter+=" and ";

        }
        if(valueParentReferalCode.size()!=0){
            for(int i=0;i<valueParentReferalCode.size();i++){
                sqlFilter+=valueParentReferalCode.get(i);
            }
            if(valueIsActive.size()!=0
                ||valueIsVerified.size()!=0)
                sqlFilter+=" and ";
        }
        if(valueIsActive.size()!=0){
            sqlFilter+=valueIsActive.get(0);
            if(valueIsVerified.size()!=0)
                sqlFilter+=" and ";
        }
        if(valueIsVerified.size()!=0){
            sqlFilter+=valueIsVerified.get(0);
//            if(valueIsVerified.size()!=0)
//                sqlFilter+=" and ";
        }

        String sqls = sql+" "
                + sqlFilter
                + " order by name asc "
                + ((pageSize > 0) ? " limit " + ((pageNum - 1) * pageSize) + "," + pageSize : "");

        Query query = entityManager.createNativeQuery(sqls, User.class);
        System.out.println(sqls);
        for (String key : params.keySet()) {

            query.setParameter(key, params.get(key));
        }

        List<User> userList = query.getResultList();

        for (User user : userList) {

            UserDTO dto = new UserDTO();
            try{
                Optional<Tier> tier= tierRepository.findById(Long.valueOf(user.getTierId()));
                dto.setTierName(tier.get().getTierName());

            }catch (Exception e){

            }

            BeanUtils.copyProperties(user, dto);
            dto.setPass("");
            dto.setIdCardImage("");
            result.add(dto);
        }

        return result;
    }

    public boolean verify(CheckVerifiedDto checkVerifiedDto) throws Exception {

        User user = userRepo.findByUserId(checkVerifiedDto.getUserId());

        if (user != null) {

            Integer val = userRepo.findIsValidByReferal(checkVerifiedDto.getReferalCode(), checkVerifiedDto.getUserId());

            if (val == null)
                throw new Exception("Wrong Referal Code");
            User oldUser=new User();
            BeanUtils.copyProperties(user,oldUser);
            user.setVerifiedTime(new Date());
            user.setIsVerified(1);
            userRepo.save(user);
            auditService.update("user",user.getUserId(),oldUser,user);
            return true;
        }
        else
            throw new Exception("User not found");
    }

    public boolean activeInActive(ActiveInActiveUserDTO request) throws Exception {

        User user = userRepo.findByUserId(request.getUserId());
        User oldUser=new User();
        BeanUtils.copyProperties(user,oldUser);
        if (user != null) {

            request.setStatus(request.getStatus().toLowerCase());

            if (!request.getStatus().contains("in")||request.getStatus().equals("1"))
                user.setIsActive(1);
            else
                user.setIsActive(0);


            userRepo.save(user);
            auditService.update("user",request.getUpdatedBy(),oldUser,user);
            List<String>destination=new ArrayList<>();
            destination.add(request.getUserId());
            destination.add(user.getName());
            Optional<EmailTemplate> emailTemplate=templateRepository.findById(user.getIsActive()==0?3l:4L);
            String partBody=emailTemplate.get().getHtmlTags();
            System.out.println(partBody);
            Date date=new Date();
            partBody=partBody.replaceAll("Name",user.getName());
            partBody=partBody.replaceAll("dateTime",simpleDateFormat.format(date));
            partBody=partBody.replaceAll("admin",request.getUpdatedBy());
            emailService.mapperMails(destination,partBody);
            System.out.println(partBody);
            return true;
        }
        else
            throw new Exception("User not found");
    }

    public boolean logout(logoutDTO request) throws Exception {

        User user = userRepo.findByUserId(request.getUserId());

        if (user != null) {

            user.setToken(null);
            userRepo.save(user);

            return true;
        }
        else
            throw new Exception("User not found");
    }

    public boolean generateOTP(OtpRqDto request) throws Exception {

        if(request.getMode()==1){
            User user = userRepo.findByUserId(request.getUserId());

            if (user != null) {
                Tokenisation tokenBefore = tokenizationRepository.findByUserIdAndTokenType(user.getId(), 1);
                System.out.println(tokenBefore);
                Tokenisation token = Tokenisation.builder()
                        .tokenType(1)
                        .expiredTime(Date.from(LocalDateTime.now().plusSeconds(otpExpired).toInstant(ZoneOffset.UTC)))
                        .phoneNumber(request.getPhoneNumber() == null ? user.getPhoneNumber() : request.getPhoneNumber())
                        .tokenNumber(Util.generateNumber())
                        .userId(user.getId())
                        .build();
                if (tokenBefore!=null)
                    token.setId(tokenBefore.getId());

                tokenizationRepository.save(token);
                //List<String>destination=new ArrayList<>();
                //destination.add(user.getUserId());
                //destination.add(user.getName());
                
                //String partBody="UserName :"+user.getUserId()+""+"<br> "+"<p><br>OTP : "+token.getTokenNumber()+"</p>";
                
                //emailService.mapperMails(destination,partBody);
                
                // todo : use template from database
                String msg = "Velo OTP : " + token.getTokenNumber();                
                msgService.send(token.getPhoneNumber(), msg);

                return true;
            }
            else
                throw new Exception("User not found");
        }
        else{
            Tokenisation token= Tokenisation.builder().tokenType(1).
                    expiredTime(Date.from(LocalDateTime.now().plusSeconds(otpExpired).toInstant(ZoneOffset.UTC)))
                    .phoneNumber(request.getPhoneNumber()).tokenNumber(Util.generateNumber()).build();
            
            tokenizationRepository.save(token);
                    
            // todo : use template from database
            String msg = "Velo OTP : " + token.getTokenNumber();                
            msgService.send(token.getPhoneNumber(), msg);
                
            return true;
        }

    }

    public boolean  changePhone(ChangePhoneRqDTO request) throws Exception {
        if(request.getUserId()!=null&&!request.getUserId().isEmpty()){
            User user = userRepo.findByUserId(request.getUserId());
            User oldUser=new User();
            BeanUtils.copyProperties(user,oldUser);
            if (user != null) {

                Tokenisation token = tokenizationRepository.findToken(user.getId(), request.getOtp(), 1);

                if ((token != null) &&
                        LocalDateTime.now().isBefore(
                                token.getExpiredTime()
                                        .toInstant().atZone(ZoneId.systemDefault())
                                        .toLocalDateTime())) {

                    user.setPhoneNumber(request.getPhoneNumber());
                    userRepo.save(user);
                    auditService.update("User",request.getUserId(),oldUser,user);
                    return true;
                }
                else
                    throw new Exception("OTP not valid");
            }
            else
                throw new Exception("User not found");
        }else{
            Tokenisation token=tokenizationRepository.findByTokenTypeAndTokenNumberAndPhoneNumber(1,request.getOtp(),request.getPhoneNumber());
            if ((token != null) &&
                    LocalDateTime.now().isBefore(
                            token.getExpiredTime()
                                    .toInstant().atZone(ZoneId.systemDefault())
                                    .toLocalDateTime())) {
                    tokenizationRepository.delete(token);
                return true;
            }else
                throw new Exception("OTP not valid");
        }

    }

    public boolean changePassword(ChangePasswordRqDTO request) throws Exception {

        User user = userRepo.findByUserId(request.getUserId());
        User oldUser=new User();
        BeanUtils.copyProperties(user,oldUser);
        if (user != null) {
            if (encoder.matches(request.getOldPass(), user.getPass())) {
                user.setPass(encoder.encode(request.getPass()));

                userRepo.save(user);
                auditService.update("User",request.getUserId(),oldUser,user);
            }else
                throw new Exception("Current Password Is Invalid");

        }
        else
            throw new Exception("User not found");

        return true;
    }


    public boolean changeEmailRequest(ChangeEmailDto request) throws Exception {

        User user = userRepo.findByUserId(request.getUserId());

        if (user != null){
            System.out.println(request);
            User existingUser = userRepo.findByUserId(request.getEmail());

            if (existingUser != null)
                throw new Exception("Email has Been Used");

            Tokenisation tokenBefore = tokenizationRepository.findByUserIdAndTokenType(user.getId(), 3);

            Tokenisation token = Tokenisation.builder()
                    .tokenType(3)
                    .expiredTime(Date.from(LocalDateTime.now().plusSeconds(emailExpired).toInstant(ZoneOffset.UTC)))
                    .email(request.getEmail())
                    .tokenNumber(UUID.randomUUID().toString())
                    .userId(user.getId())
                    .build();

            if (tokenBefore!=null)
                token.setId(tokenBefore.getId());

            tokenizationRepository.save(token);
            List<String>destination=new ArrayList<>();
            destination.add(request.getEmail());
            destination.add(user.getName());
            String mailUrl="http://52.77.161.168/confirmemail.html?token="+token.getTokenNumber();
            String partBody="UserName :"+request.getUserId()+""+"<br> "+"<p>test <b>aja</b> Change Email <a href='"+mailUrl+"'>Change Email</a></p>";
            emailService.mapperMails(destination,partBody);
            // to do send email to server

            return true;
        }
        else
            throw new Exception("User not found");
    }

    public UserDTO changeEmailGet(String token) throws Exception {

        Tokenisation tokenObj = tokenizationRepository.findByTokenNumberAndTokenTypes(token, 3);

        if (tokenObj != null) {

            Optional<User> user = userRepo.findById(tokenObj.getUserId());

            if (user.isPresent()) {

                UserDTO dto = new UserDTO();
                BeanUtils.copyProperties(user.get(), dto);
                try{
                    Optional<Tier> tier= tierRepository.findById(Long.valueOf(user.get().getTierId()));
                    dto.setTierName(tier.get().getTierName());

                }catch (Exception e){

                }

                dto.setPass("");
                dto.setIdCardImage("");

                return dto;
            }
            else
                throw new Exception("Token not valid");
        }
        else
            throw new Exception("Token not valid");
    }

    public boolean changeEmailVerify(TokenReqDto token) throws Exception {

        Tokenisation tokenObj = tokenizationRepository.findByTokenNumberAndTokenTypes(token.getToken(), 3);
        if (tokenObj != null) {

            Optional<User> user = userRepo.findById(tokenObj.getUserId());
            User oldUser=new User();
            BeanUtils.copyProperties(user.get(),oldUser);
            if (user.isPresent()) {

                user.get().setUserId(tokenObj.getEmail());

                userRepo.save(user.get());
                auditService.update("User",user.get().getUserId(),oldUser,user);
                tokenizationRepository.delete(tokenObj);
                return true;
            }
            else
                throw new Exception("Token not valid");
        }
        else
            throw new Exception("Token not valid");
    }

    public boolean forgotPasswordRequest(UserForUpdateIgniterDTO request) throws Exception {
        System.out.println(request.getUserId());
        User user = userRepo.findByUserId(request.getUserId());

        if (user != null){
            Tokenisation tokenBefore = tokenizationRepository.findByUserIdAndTokenType(user.getId(), 2);

            Tokenisation token = Tokenisation.builder()
                    .tokenType(2)
                    .expiredTime(Date.from(LocalDateTime.now().plusSeconds(emailExpired).toInstant(ZoneOffset.UTC)))
                    .email(user.getUserId())
                    .tokenNumber(UUID.randomUUID().toString())
                    .userId(user.getId())
                    .build();

            if (tokenBefore!=null)
                token.setId(tokenBefore.getId());

            tokenizationRepository.save(token);
            List<String>destination=new ArrayList<>();
            destination.add(user.getUserId());
            destination.add(user.getName());
            String mailUrl="http://52.77.161.168/resetpass.html?token="+token.getTokenNumber();
            String partBody="UserName :"+user.getUserId()+""+"<br> "+"<p>test <a href='"+mailUrl+"'>forgot password</a></p>";
            emailService.mapperMails(destination,partBody);
            // to do send email to server

            return true;
        }
        else
            throw new Exception("Email not found");
    }

    public UserDTO forgotPasswordGet(String token) throws Exception {
        System.out.println(token);
        Tokenisation tokenObj = tokenizationRepository.findByTokenNumberAndTokenTypes(token, 2);

        if (tokenObj != null) {

            Optional<User> user = userRepo.findById(tokenObj.getUserId());

            if (user.isPresent()) {

                UserDTO dto = new UserDTO();
                BeanUtils.copyProperties(user.get(), dto);
                try{
                    Optional<Tier> tier= tierRepository.findById(Long.valueOf(user.get().getTierId()));
                    dto.setTierName(tier.get().getTierName());

                }catch (Exception e){

                }

                dto.setPass("");
                dto.setIdCardImage("");
                return dto;
            }
            else
                throw new Exception("Token not valid");
        }
        else
            throw new Exception("Token not valid");
    }

    public boolean forgotPasswordVerify(VerifyPassswordDto request) throws Exception {

        Tokenisation tokenObj = tokenizationRepository.findByTokenNumberAndTokenTypes(request.getToken(), 2);

        if (tokenObj != null) {

            Optional<User> user = userRepo.findById(tokenObj.getUserId());
            User oldUser=new User();
            BeanUtils.copyProperties(user.get(),oldUser);
            if (user.isPresent()) {

                user.get().setPass(encoder.encode(request.getPassword()));
                userRepo.save(user.get());
                auditService.update("User",user.get().getUserId(),oldUser,user);
                tokenizationRepository.delete(tokenObj);
                return true;
            }
            else
                throw new Exception("Token not valid");
        }
        else
            throw new Exception("Token not valid");
    }

    public boolean deleteData(String emailAddress) throws Exception{
        User user=userRepo.findByUserId(emailAddress);

        if(user!=null){
            userRepo.delete(user);
            auditService.delete("user",user.getUserId(),user);
            return true;
        }else
            throw new Exception("User Not Found");
    }

    public String cekValidateReferalCode(String code){
        referalCode=code;
        System.out.println(referalCode);
        User cekToken=userRepo.findByReferalCode(referalCode);
        if(cekToken!=null){
            cekValidateReferalCode(Util.generateReferal());
        }
        return referalCode;
    }
    public boolean sendSms(InviteViaSmsDTO request) throws Exception {
        HashMap<String,HashMap<String,Object>>returner=new HashMap<>();
        User senders=userRepo.findByUserId(request.getUserId());
        if(senders!=null){
            for (String recepient: request.getReceiver()) {
//            JsonNode jsonNode= (JsonNode) smsGatewayService.getSmsToken();
//            String tokens=jsonNode.get("access_token").asText();
//            System.out.println(tokens);
//            senders.put("sender","087878144347");
//            senders.put("recipient",recepient);
//            senders.put("message","send test Message");
//            JsonNode node= (JsonNode) smsGatewayService.sendSmsToClient(tokens,senders);
//
//
//            HashMap<String,Object>messages=new HashMap<>();
//            messages.put("status",node !=null?true:false);
//            returner.put("data to"+recepient,messages);
                String msg="Gunakan kode berikut "+senders.getReferalCode()+" untuk daftar ke Velo Apps. Segera Unduh aplikasi di www.detik.com";
                msgService.send(recepient, msg);

            }
        }else
            throw new Exception("user not found");

        return true;
    }

    public boolean sendInvitation(InvitationDTO request) throws Exception {
        User user = userRepo.findByUserId(request.getUserId());
        if(user!=null){
            user.setInvitationStatus(request.getInvitationStatus());
            userRepo.save(user);
            return true;
        }else{
            throw new Exception("User Not Found");
        }
    }

    public List<UserDTO> findbyStatus(InvitationDTO request) throws Exception{
        List<UserDTO> result = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        String query="select * from vlo_user vu where ";
        List<String>valueUserId=new ArrayList<>();
        String valOthers;
        if(request.getUserId()!=null&&!request.getUserId().isEmpty()){
            List<String> listParams = Arrays.asList(request.getUserId().split(","));

            int no = 0;
            for (String param: listParams) {
                valueUserId.add(" email_address like :create"+no);
                params.put("create" + no, "%"+param+"%");
                no++;

            }
        }
        if(valueUserId.size()!=0){
            for(int i=0;i<valueUserId.size();i++){
                query+=valueUserId.get(i);
                if(i!=valueUserId.size()-1){
                    query+=" or";
                }
            }
            if(request.getInvitationStatus()!=null){
                query+=" and ";
            }
        }
        if(request.getInvitationStatus()!=null){
            query+=" invitation_status=:status";
            params.put("status",request.getInvitationStatus());
        }
        Query queri = entityManager.createNativeQuery(query, User.class);
        for (String key : params.keySet()) {

            queri.setParameter(key, params.get(key));
        }

        List<User> userList = queri.getResultList();

        for (User user : userList) {

            UserDTO dto = new UserDTO();
            try{
                Optional<Tier> tier= tierRepository.findById(Long.valueOf(user.getTierId()));
                dto.setTierName(tier.get().getTierName());

            }catch (Exception e){

            }

            BeanUtils.copyProperties(user, dto);
            dto.setPass("");
            dto.setIdCardImage("");
            result.add(dto);
        }
        return result;
    }
    
    public void addFile(String userName, String fileType, String fileName, String fileData) throws Exception {
    
        User user = userRepo.findByUserId(userName);
        
        if (user != null) {
            
            UserFile file = fileRepo.findByUserIdAndFileType(user.getId(), fileType);
            
            if (file == null) {
                
                file = UserFile.builder()
                        .userId(user.getId())
                        .fileType(fileType)
                        .build();
            }
            
            file.setFileData(fileData);
            file.setFileName(fileName);
            
            fileRepo.save(file);
        }
        else
            throw new Exception("User not found");
    }
    
    public UserFileDTO getFile(String userName, String fileType) throws Exception {
        
        UserFileDTO ret = null;
        User user = userRepo.findByUserId(userName);

        if (user != null) {
            
            UserFile file = fileRepo.findByUserIdAndFileType(user.getId(), fileType);
            
            if (file == null)
                throw new Exception("File not found");
            
            ret = new UserFileDTO();
            BeanUtils.copyProperties(file, ret);
        }
        else
            throw new Exception("User not found");
        
        return ret;
    }

    public void promoteIgniter(String referalCode, String userId, String updateUser) throws Exception{
        
        User user = userRepo.findByReferalCode(referalCode);
        
        if (user != null) {
            
            if (user.getUserId().equalsIgnoreCase(userId)) {
                
                if (user.getRoleCode() == 4) {
                
                    if ((user.getEligibleForIgniter() != null) &&(user.getEligibleForIgniter() == 1)) {
                        
                        if ((user.getInvitationStatus() != null) &&(user.getInvitationStatus() == 1)) {

                            User updated = new User();

                            BeanUtils.copyProperties(user, updated);

                            updated.setRoleCode(3);
                            updated.setParentReferalCode(null);
                            updated.setUpdatedBy(updateUser);
                            updated.setUpdatedDate(new Date());

                            userRepo.save(updated);

                            auditService.update("user", updateUser, user, updated);
                        }
                        else
                            throw new Exception("User havent approve the invitation");
                    }
                    else
                        throw new Exception("User not eligible for promote to igniter");
                }
                else
                    throw new Exception("User is not a fueler");                
            }
            else
                throw new Exception("User Id is different with recorded data");
        }
        else
            throw new Exception("User not found");
    }
}
