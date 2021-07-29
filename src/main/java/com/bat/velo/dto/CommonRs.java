package com.bat.velo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommonRs<T> {
 
    @JsonInclude(JsonInclude.Include.NON_NULL)
	
    protected int status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected T data;

    public CommonRs(int status, String message) {
            
        super();
        this.status = status;
        this.message = message;
    }
}
