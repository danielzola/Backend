package com.bat.velo.mail.service;

import com.bat.velo.mail.dto.Content;
import com.bat.velo.mail.dto.FullMail;
import com.bat.velo.mail.dto.IdentityMail;
import com.bat.velo.mail.dto.PersonalizationMail;
import com.google.gson.Gson;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmailService {

    private FullMail fullMail;

    @Value("${email.sender}")
    private String sender;

    @Value("${email.sender_name}")
    private String senderName;

    @Value("${email.apy_key}")
    private String apiKey;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    public void mapperMails(List<String> destination,String partOfBody){
        fullMail = new FullMail();
        IdentityMail from=new IdentityMail();
        from.setEmail(sender);
        from.setName(senderName);
        fullMail.setFrom(from);
        fullMail.setSubject("IgnitionMails");
        List<Content> content=new ArrayList<>();
        Content cont=new Content();
        cont.setType("html");
        String htmlCode= StringEscapeUtils.unescapeHtml4(partOfBody);
        cont.setValue(htmlCode);
//                cont.setValue("Test");
        content.add(cont);
        fullMail.setContent(content);
        IdentityMail toData=new IdentityMail();
        toData.setEmail(destination.get(0));
        toData.setName(destination.get(1));
        List<IdentityMail>toDatas=new ArrayList<>();
        toDatas.add(toData);
        PersonalizationMail personalizationMail=new PersonalizationMail();
        personalizationMail.setTo(toDatas);
        List<PersonalizationMail>personalizationMails=new ArrayList<>();
        personalizationMails.add(personalizationMail);
        fullMail.setPersonalizations(personalizationMails);
        Gson gson=new Gson();
        System.out.println(gson.toJson(fullMail));
        // request url
        try {
            MailSender(fullMail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MailSender(FullMail fullMail) throws Exception {
        String url = "https://api.pepipost.com/v5/mail/send";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("api_key", apiKey);
        try{
            logger.debug("mail sended");
            HttpEntity<FullMail> requestEntity =
                    new HttpEntity<>(fullMail, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

        }catch (Exception e){
            throw new Exception("Fails");
        }

    }
}
