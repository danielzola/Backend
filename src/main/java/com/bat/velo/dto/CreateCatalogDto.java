package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateCatalogDto extends BaseDto {
    protected String nameCatalog;
    protected long categoryId;
    protected String description;
    protected String shopUrl;
    protected long status;
}
