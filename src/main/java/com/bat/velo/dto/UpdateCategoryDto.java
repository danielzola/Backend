package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCategoryDto extends BaseDto {
    protected long id;
    protected String nameCategory;
    protected String description;
}