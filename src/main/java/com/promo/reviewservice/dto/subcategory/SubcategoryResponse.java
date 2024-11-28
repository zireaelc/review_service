package com.promo.reviewservice.dto.subcategory;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Подкатегория ответ")
public record SubcategoryResponse (
        @Schema(description = "ID подкатегории")
        String id,
        @Schema(description = "Название категории", example = "Категория")
        String name,
        @Schema(description = "ID категории")
        String categoryId
){}
