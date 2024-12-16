package com.promo.reviewservice.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Категория запрос")
public record CategoryRequest (
        @Schema(description = "Название категории", example = "Категория")
        String name
){}
