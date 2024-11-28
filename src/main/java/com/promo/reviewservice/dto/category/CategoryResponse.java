package com.promo.reviewservice.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Категория ответ")
public record CategoryResponse(
        @Schema(description = "ID категории")
        String id,
        @Schema(description = "Название категории", example = "Категория")
        String name
) {}
