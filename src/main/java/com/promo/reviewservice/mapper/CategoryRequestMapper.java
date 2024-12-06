package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.category.CategoryRequest;
import com.promo.reviewservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryRequestMapper extends IModelCollect<CategoryRequest, Category>{
    Category map(CategoryRequest categoryRequest);
}
