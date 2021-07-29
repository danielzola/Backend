package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class InvitationDTO {

    @JsonProperty("status")
    protected Integer invitationStatus;

    protected String userId;
}
