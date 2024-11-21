package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Subcategory;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface SubcategoryMapper {
    Subcategory fromSubcategoryRequest(SubcategoryRequest request);
    SubcategoryResponse toSubcategoryResponse(Subcategory subcategory);
    List<SubcategoryResponse> toSubcategoryResponseList(List<Subcategory> subcategories);
    Optional<SubcategoryResponse> toSubcategoryResponseOptional(Optional<Subcategory> category);
}
