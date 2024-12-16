package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.review.ReviewResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.mapper.ReviewResponseMapper;
import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.ReviewRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final SubcategoryRepository subcategoryRepository;
    private final EmailService emailService;
    private final ReviewResponseMapper reviewMapper;

    public Page<ReviewResponse> getAllReviews(Pageable pageable) {
        var reviews = reviewRepository.findAll();
        var reviewsDTO = reviewMapper.map(reviews);
        var page = new PageImpl<>(reviewsDTO, pageable, reviewsDTO.size());
        return page;
    }

    public ReviewResponse createReview(Review review) {
        review.setText(review.getText());
        review.setRating(review.getRating());
        var subcategory = subcategoryRepository.findById(review.getSubcategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
        review.setSubcategory(subcategory);
        review = reviewRepository.save(review);
        sendReviewNotification(review, subcategory);
        return reviewMapper.map(review);
    }

    private void sendReviewNotification(Review review, Subcategory subcategory) {
        String username = getCurrentUsername();
        String subject = "New Review Created";
        String text = String.format("A new review has been created:\n\n" +
                        "User: %s\n" +
                        "Review Text: %s\n" +
                        "Rating: %d\n" +
                        "Category: %s\n" +
                        "Subcategory: %s",
                username,
                review.getText(),
                review.getRating(),
                subcategory.getCategory().getName(),
                subcategory.getName());
        var adminEmails = userService.getAdminEmails();
        for (String email : adminEmails) {
            emailService.sendSimpleMessage(email, subject, text);
        }
    }

    private String getCurrentUsername() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public Optional<ReviewResponse> getReviewById(UUID id) {
        return Optional.of(reviewMapper.map(reviewRepository.findById(id).get()));
    }

    public ReviewResponse updateReview(UUID id, Review updatedReview) {
        var result = reviewRepository.findById(id)
                .map(review -> {
                    review.setText(updatedReview.getText());
                    review.setRating(updatedReview.getRating());
                    Subcategory subcategory = subcategoryRepository.findById(updatedReview.getSubcategory().getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " +
                                    updatedReview.getSubcategory().getId()));
                    review.setSubcategory(subcategory);
                    review = reviewRepository.save(review);
                    return review;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + id));
        return reviewMapper.map(result);
    }

    public void deleteReview(UUID id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    public Page<ReviewResponse> getReviewsByDateRange(LocalDateTime startDate, LocalDateTime endDate,
                                                      Pageable pageable) {
        var reviews = reviewRepository.findByCreatedAtBetween(startDate, endDate, pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsBySubcategory(UUID subcategoryId, Pageable pageable) {
        var subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + subcategoryId));
        var reviews = reviewRepository.findBySubcategory(subcategory, pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsByCategory(UUID categoryId, Pageable pageable) {
        var reviews = reviewRepository.findByCategory(categoryId, pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsSortedByRatingAsc(Pageable pageable) {
        var reviews = reviewRepository.findAllByOrderByRatingAsc(pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsSortedByRatingDesc(Pageable pageable) {
        var reviews = reviewRepository.findAllByOrderByRatingDesc(pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsSortedByDateAsc(Pageable pageable) {
        var reviews = reviewRepository.findAllByOrderByCreatedAtAsc(pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsSortedByDateDesc(Pageable pageable) {
        var reviews = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsByDateRangeSortedByDateDesc(LocalDateTime startDate,
                                                                      LocalDateTime endDate, Pageable pageable) {
        var reviews = reviewRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startDate, endDate, pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsBySubcategorySortedByRatingDesc(UUID subcategoryId, Pageable pageable) {
        var subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + subcategoryId));
        var reviews = reviewRepository.findBySubcategoryOrderByRatingDesc(subcategory, pageable);
        return reviews.map(reviewMapper::map);
    }

    public Page<ReviewResponse> getReviewsByCategorySortedByDateDesc(UUID categoryId, Pageable pageable) {
        var reviews = reviewRepository.findByCategoryOrderByCreatedAtDesc(categoryId, pageable);
        return reviews.map(reviewMapper::map);
    }
}
