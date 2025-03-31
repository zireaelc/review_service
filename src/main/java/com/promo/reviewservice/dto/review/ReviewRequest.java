package com.promo.reviewservice.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Отзыв запрос")
public record ReviewRequest (
        @Schema(description = "Текст отзыва", example = "Мне понравилось!")
        String text,
        @Schema(description = "Оценка", example = "5")
        int rating,
        @Schema(description = "ID подкатегории")
        String subcategoryId
){}
