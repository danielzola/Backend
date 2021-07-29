package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InviteViaSmsDTO {
    
    protected String userId;
    protected String channel;
    protected List<String> receiver;
}
