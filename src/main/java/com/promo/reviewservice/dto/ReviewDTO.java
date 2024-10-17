package com.promo.reviewservice.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private String text;
    private int rating;
    private Long subcategoryId;
}
