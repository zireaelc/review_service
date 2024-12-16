package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryResponseMapper extends IModelCollect<Category, CategoryResponse>{
    CategoryResponse map(Category category);
}
