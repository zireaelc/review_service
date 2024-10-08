package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.ReviewDTO;
import com.promo.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
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
    public List<ReviewDTO> getReviewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return reviewService.getReviewsByDateRange(startDate, endDate);
    }

    @GetMapping("/by-subcategory/{subcategoryId}")
    public List<ReviewDTO> getReviewsBySubcategory(@PathVariable Long subcategoryId) {
        return reviewService.getReviewsBySubcategory(subcategoryId);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<ReviewDTO> getReviewsByCategory(@PathVariable Long categoryId) {
        return reviewService.getReviewsByCategory(categoryId);
    }

    @GetMapping("/sorted-by-rating-asc")
    public List<ReviewDTO> getReviewsSortedByRatingAsc() {
        return reviewService.getReviewsSortedByRatingAsc();
    }

    @GetMapping("/sorted-by-rating-desc")
    public List<ReviewDTO> getReviewsSortedByRatingDesc() {
        return reviewService.getReviewsSortedByRatingDesc();
    }

    @GetMapping("/sorted-by-date-asc")
    public List<ReviewDTO> getReviewsSortedByDateAsc() {
        return reviewService.getReviewsSortedByDateAsc();
    }

    @GetMapping("/sorted-by-date-desc")
    public List<ReviewDTO> getReviewsSortedByDateDesc() {
        return reviewService.getReviewsSortedByDateDesc();
    }

    @GetMapping("/by-date-range-sorted-by-date-desc")
    public List<ReviewDTO> getReviewsByDateRangeSortedByDateDesc(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return reviewService.getReviewsByDateRangeSortedByDateDesc(startDate, endDate);
    }

    @GetMapping("/by-subcategory-sorted-by-rating-desc/{subcategoryId}")
    public List<ReviewDTO> getReviewsBySubcategorySortedByRatingDesc(@PathVariable Long subcategoryId) {
        return reviewService.getReviewsBySubcategorySortedByRatingDesc(subcategoryId);
    }

    @GetMapping("/by-category-sorted-by-date-desc/{categoryId}")
    public List<ReviewDTO> getReviewsByCategorySortedByDateDesc(@PathVariable Long categoryId) {
        return reviewService.getReviewsByCategorySortedByDateDesc(categoryId);
    }
}
