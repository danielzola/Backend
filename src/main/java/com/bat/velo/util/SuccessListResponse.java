package com.bat.velo.util;

import lombok.Data;

import java.util.List;

@Data
public class SuccessListResponse<T> {
    String status;
    String message;
    List<T> items;
    Integer totalCount;
}
