package com.bat.velo.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalizationMail {
    protected List<IdentityMail>to=new ArrayList<>();
    protected List<IdentityMail>cc=new ArrayList<>();
    protected List<IdentityMail>bcc=new ArrayList<>();
}
