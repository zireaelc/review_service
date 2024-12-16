package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.model.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubcategoryResponseMapper extends IModelCollect<Subcategory, SubcategoryResponse> {
    @Mapping(target = "categoryId", source = "category.id")
    SubcategoryResponse map(Subcategory subcategory);
}
