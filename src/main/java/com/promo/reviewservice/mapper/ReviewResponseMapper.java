package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.review.ReviewResponse;
import com.promo.reviewservice.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewResponseMapper extends IModelCollect<Review, ReviewResponse> {
    @Mapping(target = "subcategoryId", source = "subcategory.id")
    ReviewResponse map(Review review);
}
