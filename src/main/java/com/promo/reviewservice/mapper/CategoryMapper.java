package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.category.CategoryRequest;
import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category fromCategoryRequest(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}
