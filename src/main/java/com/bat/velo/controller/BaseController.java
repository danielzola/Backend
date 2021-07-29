package com.bat.velo.controller;

import com.bat.velo.dto.CommonRs;
//import com.bat.velo.dto.CommonRs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    public <T> ResponseEntity<CommonRs<T>> ok(T data) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.OK.value(), "success", data), HttpStatus.OK);
    }

    public ResponseEntity<CommonRs> ok() {
        return new ResponseEntity<>(new CommonRs(HttpStatus.OK.value(), "success"), HttpStatus.OK);
    }

    public <T> ResponseEntity<CommonRs<T>> created(T data) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.CREATED.value(), "created", data), HttpStatus.CREATED);
    }

    public ResponseEntity created() {
        return new ResponseEntity<>(new CommonRs(HttpStatus.CREATED.value(), "created"), HttpStatus.CREATED);
    }

    public <T> ResponseEntity<CommonRs<T>> badRequest(T data) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.BAD_REQUEST.value(), "bad request", data), HttpStatus.BAD_REQUEST);
    }
    
    public <T> ResponseEntity<CommonRs<T>> badRequest(String msg) {
        return new ResponseEntity<>(new CommonRs(HttpStatus.BAD_REQUEST.value(),  msg), HttpStatus.BAD_REQUEST);
    }
}
