package com.promo.reviewservice.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewRequest {
    @NotBlank
    @Size(min = 1, max = 500)
    private String text;

    @NotNull
    @Min(1)
    @Max(5)
    private int rating;

    @NotNull
    private UUID subcategoryId;
}
