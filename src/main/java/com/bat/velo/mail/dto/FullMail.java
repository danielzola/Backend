package com.bat.velo.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FullMail {
    protected IdentityMail from=new IdentityMail();
    protected String subject;
    protected List<Content>content=new ArrayList<>();
    protected List<PersonalizationMail> personalizations =new ArrayList<>();
}
