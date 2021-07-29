package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCategoryDto extends BaseDto {
    protected String nameCategory;
    protected String description;
}
