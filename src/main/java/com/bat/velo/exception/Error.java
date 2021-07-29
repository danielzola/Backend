package com.bat.velo.exception;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonRootName(value = "error")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private int statusCode;
    private String statusDescription;
    private String errorMessage;
}
