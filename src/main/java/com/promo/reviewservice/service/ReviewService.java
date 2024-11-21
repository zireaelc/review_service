package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.review.ReviewResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.mapper.ReviewMapper;
import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.ReviewRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final SubcategoryRepository subcategoryRepository;
    private final EmailService emailService;
    private final ReviewMapper reviewMapper;

    public Page<ReviewResponse> getAllReviews(Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findAll(pageable));
    }

    public ReviewResponse createReview(Review review) {
        review.setText(review.getText());
        review.setRating(review.getRating());
        Subcategory subcategory = subcategoryRepository.findById(review.getSubcategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
        review.setSubcategory(subcategory);
        review = reviewRepository.save(review);
        sendReviewNotification(review, subcategory);
        return reviewMapper.toReviewResponse(review);
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
        List<String> adminEmails = userService.getAdminEmails();
        for (String email : adminEmails) {
            emailService.sendSimpleMessage(email, subject, text);
        }
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public Optional<ReviewResponse> getReviewById(UUID id) {
        return reviewMapper.toReviewResponseOptional(reviewRepository.findById(id));
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
        return reviewMapper.toReviewResponse(result);
    }

    public void deleteReview(UUID id) {
        reviewRepository.deleteById(id);
    }

    public Page<ReviewResponse> getReviewsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findByCreatedAtBetween(startDate, endDate, pageable));
    }

    public Page<ReviewResponse> getReviewsBySubcategory(UUID subcategoryId, Pageable pageable) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + subcategoryId));
        return reviewMapper.toReviewResponsePage(reviewRepository.findBySubcategory(subcategory, pageable));
    }

    public Page<ReviewResponse> getReviewsByCategory(UUID categoryId, Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findByCategory(categoryId, pageable));
    }

    public Page<ReviewResponse> getReviewsSortedByRatingAsc(Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findAllByOrderByRatingAsc(pageable));
    }

    public Page<ReviewResponse> getReviewsSortedByRatingDesc(Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findAllByOrderByRatingDesc(pageable));
    }

    public Page<ReviewResponse> getReviewsSortedByDateAsc(Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findAllByOrderByCreatedAtAsc(pageable));
    }

    public Page<ReviewResponse> getReviewsSortedByDateDesc(Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findAllByOrderByCreatedAtDesc(pageable));
    }

    public Page<ReviewResponse> getReviewsByDateRangeSortedByDateDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startDate, endDate, pageable));
    }

    public Page<ReviewResponse> getReviewsBySubcategorySortedByRatingDesc(UUID subcategoryId, Pageable pageable) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + subcategoryId));
        return reviewMapper.toReviewResponsePage(reviewRepository.findBySubcategoryOrderByRatingDesc(subcategory, pageable));
    }

    public Page<ReviewResponse> getReviewsByCategorySortedByDateDesc(UUID categoryId, Pageable pageable) {
        return reviewMapper.toReviewResponsePage(reviewRepository.findByCategoryOrderByCreatedAtDesc(categoryId, pageable));
    }
}
