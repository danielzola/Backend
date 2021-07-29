package com.bat.velo.service;

import com.bat.velo.entity.MessagePool;
import com.bat.velo.repository.MessagePoolRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class MessagePoolService {

    @Value("${smsGateway.user}")
    private String user;
    
    @Value("${smsGateway.pass}")
    private String pass;

    @Value("${smsGateway.sender}")
    private String sender;

    @Value("${smsGateway.urlGetToken}")
    private String urlGetToken;

    @Value("${smsGateway.urlSendSms}")
    private String urlSendSms;
    
    @Autowired
    protected MessagePoolRepository msgRepo;
    
    @Autowired
    protected ObjectMapper objMapper;
            
    public void send(String receiver, String msgString) {
    
        MessagePool msg = MessagePool.builder()
                            .channel("SMS")
                            .msg(msgString)
                            .nextTry(new Date())
                            .maxTry(3)
                            .status(0)
                            .receiver(receiver)
                            .build();
        
        msgRepo.save(msg);
    }
    
    public String getSmsToken() {
        
        String ret = null;
        
        try {
            
            String getUserPassword = user + ":" + pass ;
            String base64encode = Base64.getEncoder().encodeToString(getUserPassword.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(base64encode);
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type","client_credentials");
            
            HttpEntity request = new HttpEntity(body, headers);

            ResponseEntity<String> response = new RestTemplate().exchange(urlGetToken, HttpMethod.POST, request, String.class);

            if (response.getBody() != null) {
                
                System.out.println("get token ===> " + response.getBody());
                
                SMSTokenRs result = objMapper.readValue(response.getBody(), SMSTokenRs.class);
                        
                // refreshing token

                body = new LinkedMultiValueMap<>();
                body.add("grant_type","refresh_token");
                body.add("refresh_token", result.getRefresh_token());
                
                request = new HttpEntity(body, headers);
                response = new RestTemplate().exchange(urlGetToken, HttpMethod.POST, request, String.class);
            
                if (response.getBody() != null) {
                 
                    System.out.println("refresh token ===> " + response.getBody());
                
                    result = objMapper.readValue(response.getBody(), SMSTokenRs.class);
                
                    System.out.println(result.getAccess_token());
                    System.out.println(result.getRefresh_token());

                    ret = result.getAccess_token();
                }
            }
            else {
                System.out.println("SMSM Token error : " + response.getBody());
            }
        } 
        catch (Exception x) {
            
            x.printStackTrace();
        }
        
        return ret;
    }
    
    private void sendSMS(String token, String to, String msg) throws Exception {
        
        if (to.startsWith("0"))
            to = "62" + to.substring(1);
        
        if (to.startsWith("+"))
            to = to.substring(1);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("action", "sendSMSB");
        body.add("sender", sender);
        body.add("recipient", to);
        body.add("message", msg);
        body.add("msg_id", UUID.randomUUID().toString());
        body.add("encoding", "0");
        
        HttpEntity request = new HttpEntity(body, headers);
        
        ResponseEntity<String> response = new RestTemplate().exchange(urlSendSms, HttpMethod.POST, request, String.class);

        if (response.getBody() != null) {
            
            System.out.println("Send SMS ===> " + response.getBody());
        }
        else
            throw new Exception("send sms server return null");
    }
    
    @Scheduled(fixedDelay = 10000) // 10s
    public void checkSMSPool() {
        
        List<MessagePool> list = msgRepo.findPool("SMS", new Date());
        
        if (list.size() > 0) {
            
            String token = getSmsToken();
            System.out.println("=============> token : " + token);
            
            for (MessagePool msg : list) {

                try {
                    
                    sendSMS(token, msg.getReceiver(), msg.getMsg());
                    
                    msg.setStatus(1);
                    msgRepo.save(msg);
                }
                catch (Exception e) {

                    msg.setMaxTry(msg.getMaxTry() - 1);

                    if (msg.getMaxTry() <= 0)
                        msg.setStatus(-1);
                    else {

                        long ONE_MINUTE_IN_MILLIS=60000;//millisecs

                        Calendar date = Calendar.getInstance();
                        long t = date.getTimeInMillis();
                        Date nextTry = new Date(t + (1 * ONE_MINUTE_IN_MILLIS));

                        msg.setNextTry(nextTry);
                    }

                    msgRepo.save(msg);
                }
            }
        }
    }
}
