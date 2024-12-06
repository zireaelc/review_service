package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.review.ReviewRequest;
import com.promo.reviewservice.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewRequestMapper extends IModelCollect<ReviewRequest, Review> {
    @Mapping(target = "subcategory.id", source = "subcategoryId")
    Review map(ReviewRequest reviewRequest);
}
