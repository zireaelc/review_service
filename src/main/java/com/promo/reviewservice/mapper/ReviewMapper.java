package com.promo.reviewservice.mapper;

import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.dto.review.ReviewRequest;
import com.promo.reviewservice.dto.review.ReviewResponse;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Review;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review fromReviewRequest(ReviewRequest request);
    ReviewResponse toReviewResponse(Review review);
    Page<ReviewResponse> toReviewResponsePage(Page<Review> page);
    Optional<ReviewResponse> toReviewResponseOptional(Optional<Review> review);
}
