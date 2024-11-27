package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubcategoryMapper {
    @Mapping(target = "category.id", source = "categoryId")
    Subcategory fromSubcategoryRequest(SubcategoryRequest request);

    @Mapping(target = "categoryId", source = "category.id")
    SubcategoryResponse toSubcategoryResponse(Subcategory subcategory);

    @Mapping(target = "categoryId", source = "category.id")
    List<SubcategoryResponse> toSubcategoryResponseList(List<Subcategory> subcategories);
}
