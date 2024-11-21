package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.ReviewDTO;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
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

    public Page<Review> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable);
    }

    public Review createReview(Review review) {
        review.setText(review.getText());
        review.setRating(review.getRating());
        Subcategory subcategory = subcategoryRepository.findById(review.getSubcategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found"));
        review.setSubcategory(subcategory);
        review = reviewRepository.save(review);
        sendReviewNotification(review, subcategory);
        return review;
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

    public Optional<Review> getReviewById(UUID id) {
        return reviewRepository.findById(id);
    }

    public Review updateReview(UUID id, Review updatedReview) {
        return reviewRepository.findById(id)
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
    }

    public void deleteReview(UUID id) {
        reviewRepository.deleteById(id);
    }

    public Page<Review> getReviewsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return reviewRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    public Page<Review> getReviewsBySubcategory(UUID subcategoryId, Pageable pageable) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + subcategoryId));
        return reviewRepository.findBySubcategory(subcategory, pageable);
    }

    public Page<Review> getReviewsByCategory(UUID categoryId, Pageable pageable) {
        return reviewRepository.findByCategory(categoryId, pageable);
    }

    public Page<Review> getReviewsSortedByRatingAsc(Pageable pageable) {
        return reviewRepository.findAllByOrderByRatingAsc(pageable);
    }

    public Page<Review> getReviewsSortedByRatingDesc(Pageable pageable) {
        return reviewRepository.findAllByOrderByRatingDesc(pageable);
    }

    public Page<Review> getReviewsSortedByDateAsc(Pageable pageable) {
        return reviewRepository.findAllByOrderByCreatedAtAsc(pageable);
    }

    public Page<Review> getReviewsSortedByDateDesc(Pageable pageable) {
        return reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Page<Review> getReviewsByDateRangeSortedByDateDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return reviewRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startDate, endDate, pageable);
    }

    public Page<Review> getReviewsBySubcategorySortedByRatingDesc(UUID subcategoryId, Pageable pageable) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + subcategoryId));
        return reviewRepository.findBySubcategoryOrderByRatingDesc(subcategory, pageable);
    }

    public Page<Review> getReviewsByCategorySortedByDateDesc(UUID categoryId, Pageable pageable) {
        return reviewRepository.findByCategoryOrderByCreatedAtDesc(categoryId, pageable);
    }
}
