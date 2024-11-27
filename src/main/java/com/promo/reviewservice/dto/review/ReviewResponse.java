package com.promo.reviewservice.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;

public record ReviewResponse (
        @Schema(description = "ID отзыва")
        String id,
        @Schema(description = "Текст отзыва", example = "Мне понравилось!")
        String text,
        @Schema(description = "Оценка", example = "5")
        int rating,
        @Schema(description = "ID подкатегории")
        String subcategoryId
){}
