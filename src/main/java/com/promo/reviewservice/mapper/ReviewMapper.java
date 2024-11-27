package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.dto.review.ReviewRequest;
import com.promo.reviewservice.dto.review.ReviewResponse;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReviewMapper {
    @Mapping(target = "subcategory.id", source = "subcategoryId")
    Review fromReviewRequest(ReviewRequest request);

    @Mapping(target = "subcategoryId", source = "subcategory.id")
    ReviewResponse toReviewResponse(Review review);

    @Mapping(target = "subcategoryId", source = "subcategory.id")
    List<ReviewResponse> toReviewResponseList(List<Review> reviews);
}
