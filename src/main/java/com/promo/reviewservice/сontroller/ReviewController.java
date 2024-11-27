package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.review.ReviewRequest;
import com.promo.reviewservice.dto.review.ReviewResponse;
import com.promo.reviewservice.mapper.ReviewMapper;
import com.promo.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @GetMapping("/reviews")
    public Page<ReviewResponse> getAllReviews(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getAllReviews(pageable);
    }

    @PostMapping("/reviews")
    public ReviewResponse createReview(@RequestBody ReviewRequest reviewRequest) {
        return reviewService.createReview(reviewMapper.fromReviewRequest(reviewRequest));
    }

    @GetMapping("/reviews/{id}")
    public ReviewResponse getReviewById(@PathVariable UUID id) {
        return reviewService.getReviewById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @PutMapping("/reviews/{id}")
    public ReviewResponse updateReview(@PathVariable UUID id, @RequestBody ReviewRequest updatedReviewRequest) {
        return reviewService.updateReview(id, reviewMapper.fromReviewRequest(updatedReviewRequest));
    }

    @DeleteMapping("/reviews/{id}")
    public void deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/reviews/by-date-range")
    public Page<ReviewResponse> getReviewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByDateRange(startDate, endDate, pageable);
    }

    @GetMapping("/reviews/by-subcategory/{subcategoryId}")
    public Page<ReviewResponse> getReviewsBySubcategory(@PathVariable UUID subcategoryId,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size,
                                                       @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsBySubcategory(subcategoryId, pageable);
    }

    @GetMapping("/reviews/by-category/{categoryId}")
    public Page<ReviewResponse> getReviewsByCategory(@PathVariable UUID categoryId,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByCategory(categoryId, pageable);
    }

    @GetMapping("/reviews/sorted-by-rating-asc")
    public Page<ReviewResponse> getReviewsSortedByRatingAsc(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").ascending());
        return reviewService.getReviewsSortedByRatingAsc(pageable);
    }

    @GetMapping("/reviews/sorted-by-rating-desc")
    public Page<ReviewResponse> getReviewsSortedByRatingDesc(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        return reviewService.getReviewsSortedByRatingDesc(pageable);
    }

    @GetMapping("/reviews/sorted-by-date-asc")
    public Page<ReviewResponse> getReviewsSortedByDateAsc(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        return reviewService.getReviewsSortedByDateAsc(pageable);
    }

    @GetMapping("/reviews/sorted-by-date-desc")
    public Page<ReviewResponse> getReviewsSortedByDateDesc(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewService.getReviewsSortedByDateDesc(pageable);
    }

    @GetMapping("/reviews/by-date-range-sorted-by-date-desc")
    public Page<ReviewResponse> getReviewsByDateRangeSortedByDateDesc(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByDateRangeSortedByDateDesc(startDate, endDate, pageable);
    }

    @GetMapping("/reviews/by-subcategory-sorted-by-rating-desc/{subcategoryId}")
    public Page<ReviewResponse> getReviewsBySubcategorySortedByRatingDesc(@PathVariable UUID subcategoryId,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        return reviewService.getReviewsBySubcategorySortedByRatingDesc(subcategoryId, pageable);
    }

    @GetMapping("/reviews/by-category-sorted-by-date-desc/{categoryId}")
    public Page<ReviewResponse> getReviewsByCategorySortedByDateDesc(@PathVariable UUID categoryId,
                                                                    @RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewService.getReviewsByCategorySortedByDateDesc(categoryId, pageable);
    }

    private Pageable createPageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort sortObj = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        return PageRequest.of(page, size, sortObj);
    }
}
