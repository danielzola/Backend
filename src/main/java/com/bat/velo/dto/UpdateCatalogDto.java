package com.bat.velo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateCatalogDto extends BaseDto {
    protected long id;
    protected String nameCatalog;
    protected long categoryId;
    protected String description;
    protected String shopUrl;
    protected long status;
}
