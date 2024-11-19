package com.promo.reviewservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.UUID;

@Data
public class ReviewDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

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
