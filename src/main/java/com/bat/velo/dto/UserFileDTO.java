package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class UserFileDTO {
 
    protected long id;
    protected long userId;
    protected String fileData;
    protected String fileType;
    protected String fileName;
}
