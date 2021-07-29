package com.bat.velo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FilesDTO {

    private long id;
    protected long foreignId;
    protected String fileData;
    protected String fileType;
    protected String fileName;
}
