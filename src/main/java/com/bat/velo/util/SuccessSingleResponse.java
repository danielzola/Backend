package com.bat.velo.util;

import lombok.Data;

@Data
public class SuccessSingleResponse<T> {
    String status;
    String message;
    T item;
}
