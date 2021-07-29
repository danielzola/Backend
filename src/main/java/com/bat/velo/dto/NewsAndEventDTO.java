package com.bat.velo.dto;

import com.bat.velo.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsAndEventDTO extends BaseDto {

    private long id;

    protected String title;
    
    protected String content;
    
    protected String videoUrl;
    protected long type;
    protected long status;


}
