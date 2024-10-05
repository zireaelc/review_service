package com.promo.reviewservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private String text;
    private int rating;
    private LocalDateTime createdAt;
    private Long subcategoryId;
}
