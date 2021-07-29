package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Blob;

@Data
public class UserForUpdateIgniterDTO {
    protected String userId;

}
