package com.promo.reviewservice.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryRequest (
        @Schema(description = "Название категории", example = "Категория")
        String name
){}
