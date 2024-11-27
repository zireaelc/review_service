package com.promo.reviewservice.dto.subcategory;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class SubcategoryRequest {
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    private UUID categoryId;
}
