package com.bat.velo.dto;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UploadBulkIgniterRq {
    
    protected int sucess;
    protected int fail;
    protected List<String> failList;
}
