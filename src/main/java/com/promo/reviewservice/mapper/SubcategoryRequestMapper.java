package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.model.Subcategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubcategoryRequestMapper extends IModelCollect<SubcategoryRequest, Subcategory> {
    @Mapping(target = "category.id", source = "categoryId")
    Subcategory map(SubcategoryRequest subcategoryRequest);
}
