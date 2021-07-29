package com.bat.velo.sms.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;

@Service
public class SmsGatewayService {

    @Value("${smsGateway.pass}")
    private String userPassword;

    @Value("${smsGateway.sender}")
    private String sender;

    @Value("${smsGateway.urlGetToken}")
    private String urlGetToken;

    @Value("${smsGateway.urlSendSms}")
    private String urlSendSms;

    public Object getSmsToken() {
        try {
            String getUserPassword = userPassword + ":" + userPassword + "123";
            String base64encode = Base64.getEncoder().encodeToString(getUserPassword.getBytes());

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(base64encode);
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            HttpEntity request = new HttpEntity(headers);

            ResponseEntity<String> response =
                    new RestTemplate().exchange(urlGetToken, HttpMethod.POST, request, String.class);

            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonNode = mapper.readTree(response.getBody());

            return jsonNode;

        } catch (Exception x) {
            x.printStackTrace();
            return null;
        }
    }

    public Object sendSmsToClient(String accessToken, HashMap<String,String> data) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.add("Content-Type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("action", "sendSMS");
            map.add("sender", data.get("sender"));
            map.add("recipient", data.get("recipient"));
            map.add("message", data.get("message"));
            map.add("msg_id", "7aq117a07ss14");
            map.add("encoding", "0");

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

            ResponseEntity<String> responseEntity =
                    restTemplate.exchange(urlSendSms, HttpMethod.POST, entity, String.class);
            System.out.println(responseEntity.getStatusCode());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(responseEntity.getBody());
            return jsonNode;

        } catch (Exception x) {
            x.printStackTrace();
            return null;
        }
    }
}
