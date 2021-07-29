package com.bat.velo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterCatalogDto {
    private String filter;
    private Integer categoryId;
}