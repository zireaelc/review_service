package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.CategoryDTO;
import com.promo.reviewservice.dto.ReviewDTO;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    private ReviewDTO toDto(Review entity) {
        return modelMapper.map(entity, ReviewDTO.class);
    }

    private Review toEntity(ReviewDTO dto) {
        return modelMapper.map(dto, Review.class);
    }

    @GetMapping
    public Page<ReviewDTO> getAllReviews(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getAllReviews(pageable).map(this::toDto);
    }

    @PostMapping
    public ReviewDTO createReview(@RequestBody ReviewDTO reviewDTO) {
        return toDto(reviewService.createReview(toEntity(reviewDTO)));
    }

    @GetMapping("/{id}")
    public ReviewDTO getReviewById(@PathVariable UUID id) {
        return toDto(reviewService.getReviewById(id)
                .orElseThrow(() -> new RuntimeException("Review not found")));
    }

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable UUID id, @RequestBody ReviewDTO updatedReviewDTO) {
        return toDto(reviewService.updateReview(id, toEntity(updatedReviewDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/by-date-range")
    public Page<ReviewDTO> getReviewsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByDateRange(startDate, endDate, pageable).map(this::toDto);
    }

    @GetMapping("/by-subcategory/{subcategoryId}")
    public Page<ReviewDTO> getReviewsBySubcategory(@PathVariable UUID subcategoryId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsBySubcategory(subcategoryId, pageable).map(this::toDto);
    }

    @GetMapping("/by-category/{categoryId}")
    public Page<ReviewDTO> getReviewsByCategory(@PathVariable UUID categoryId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByCategory(categoryId, pageable).map(this::toDto);
    }

    @GetMapping("/sorted-by-rating-asc")
    public Page<ReviewDTO> getReviewsSortedByRatingAsc(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").ascending());
        return reviewService.getReviewsSortedByRatingAsc(pageable).map(this::toDto);
    }

    @GetMapping("/sorted-by-rating-desc")
    public Page<ReviewDTO> getReviewsSortedByRatingDesc(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        return reviewService.getReviewsSortedByRatingDesc(pageable).map(this::toDto);
    }

    @GetMapping("/sorted-by-date-asc")
    public Page<ReviewDTO> getReviewsSortedByDateAsc(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").ascending());
        return reviewService.getReviewsSortedByDateAsc(pageable).map(this::toDto);
    }

    @GetMapping("/sorted-by-date-desc")
    public Page<ReviewDTO> getReviewsSortedByDateDesc(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewService.getReviewsSortedByDateDesc(pageable).map(this::toDto);
    }

    @GetMapping("/by-date-range-sorted-by-date-desc")
    public Page<ReviewDTO> getReviewsByDateRangeSortedByDateDesc(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort) {
        Pageable pageable = createPageable(page, size, sort);
        return reviewService.getReviewsByDateRangeSortedByDateDesc(startDate, endDate, pageable).map(this::toDto);
    }

    @GetMapping("/by-subcategory-sorted-by-rating-desc/{subcategoryId}")
    public Page<ReviewDTO> getReviewsBySubcategorySortedByRatingDesc(@PathVariable UUID subcategoryId,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("rating").descending());
        return reviewService.getReviewsBySubcategorySortedByRatingDesc(subcategoryId, pageable).map(this::toDto);
    }

    @GetMapping("/by-category-sorted-by-date-desc/{categoryId}")
    public Page<ReviewDTO> getReviewsByCategorySortedByDateDesc(@PathVariable UUID categoryId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reviewService.getReviewsByCategorySortedByDateDesc(categoryId, pageable).map(this::toDto);
    }

    private Pageable createPageable(int page, int size, String sort) {
        String[] sortParams = sort.split(",");
        Sort sortObj = Sort.by(Sort.Direction.fromString(sortParams[1]), sortParams[0]);
        return PageRequest.of(page, size, sortObj);
    }
}
