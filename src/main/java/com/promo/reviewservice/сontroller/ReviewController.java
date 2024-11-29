package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.ReviewDTO;
import com.promo.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public Page<ReviewDTO> getAllReviews(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getAllReviews(pageable);
    }

    @PostMapping
    public ReviewDTO createReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.createReview(reviewDTO);
    }

    @GetMapping("/{id}")
    public ReviewDTO getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable Long id, @RequestBody ReviewDTO updatedReviewDTO) {
        return reviewService.updateReview(id, updatedReviewDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    // Сортировки и фильтрация

    @GetMapping("/by-date-range")
    public Page<ReviewDTO> getReviewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByDateRange(startDate, endDate, pageable);
    }

    @GetMapping("/by-subcategory/{subcategoryId}")
    public Page<ReviewDTO> getReviewsBySubcategory(@PathVariable Long subcategoryId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsBySubcategory(subcategoryId, pageable);
    }

    @GetMapping("/by-category/{categoryId}")
    public Page<ReviewDTO> getReviewsByCategory(@PathVariable Long categoryId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByCategory(categoryId, pageable);
    }

    @GetMapping("/sorted-by-rating-asc")
    public Page<ReviewDTO> getReviewsSortedByRatingAsc(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").ascending());
        return reviewService.getReviewsSortedByRatingAsc(pageable);
    }

    @GetMapping("/sorted-by-rating-desc")
    public Page<ReviewDTO> getReviewsSortedByRatingDesc(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        return reviewService.getReviewsSortedByRatingDesc(pageable);
    }

    @GetMapping("/sorted-by-date-asc")
    public Page<ReviewDTO> getReviewsSortedByDateAsc(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        return reviewService.getReviewsSortedByDateAsc(pageable);
    }

    @GetMapping("/sorted-by-date-desc")
    public Page<ReviewDTO> getReviewsSortedByDateDesc(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewService.getReviewsSortedByDateDesc(pageable);
    }

    @GetMapping("/by-date-range-sorted-by-date-desc")
    public Page<ReviewDTO> getReviewsByDateRangeSortedByDateDesc(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByDateRangeSortedByDateDesc(startDate, endDate, pageable);
    }

    @GetMapping("/by-subcategory-sorted-by-rating-desc/{subcategoryId}")
    public Page<ReviewDTO> getReviewsBySubcategorySortedByRatingDesc(@PathVariable Long subcategoryId,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        return reviewService.getReviewsBySubcategorySortedByRatingDesc(subcategoryId, pageable);
    }

    @GetMapping("/by-category-sorted-by-date-desc/{categoryId}")
    public Page<ReviewDTO> getReviewsByCategorySortedByDateDesc(@PathVariable Long categoryId,
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
