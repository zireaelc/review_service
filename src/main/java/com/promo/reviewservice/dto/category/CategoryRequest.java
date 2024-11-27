package com.promo.reviewservice.dto.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
