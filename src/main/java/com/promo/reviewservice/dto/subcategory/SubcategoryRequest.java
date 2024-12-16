package com.promo.reviewservice.dto.subcategory;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Подкатегория запрос")
public record SubcategoryRequest (
        @Schema(description = "Название подкатегории", example = "Подкатегория")
        String name,
        @Schema(description = "ID категории")
        String categoryId
){}
