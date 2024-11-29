package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.ReviewDTO;
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

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final SubcategoryRepository subcategoryRepository;
    private final EmailService emailService;

    public Page<ReviewDTO> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(this::convertToDTO);
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        review.setText(reviewDTO.getText());
        review.setRating(reviewDTO.getRating());
        Subcategory subcategory = subcategoryRepository.findById(reviewDTO.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        review.setSubcategory(subcategory);
        review = reviewRepository.save(review);
        // отправка уведомления на почту
        String username = getCurrentUsername();
        String subject = "New Review Created";
        String text = String.format("A new review has been created:\n\n" +
                        "User: %s\n" +
                        "Review Text: %s\n" +
                        "Rating: %d\n" +
                        "Category: %s\n" +
                        "Subcategory: %s",
                username,
                reviewDTO.getText(),
                reviewDTO.getRating(),
                subcategory.getCategory().getName(),
                subcategory.getName());
        List<String> adminEmails = userService.getAdminEmails();
        for (String email : adminEmails) {
            emailService.sendSimpleMessage(email, subject, text);
        }
        return convertToDTO(review);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public Optional<ReviewDTO> getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(this::convertToDTO);
    }

    public ReviewDTO updateReview(Long id, ReviewDTO updatedReviewDTO) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setText(updatedReviewDTO.getText());
                    review.setRating(updatedReviewDTO.getRating());
                    Subcategory subcategory = subcategoryRepository.findById(updatedReviewDTO.getSubcategoryId())
                            .orElseThrow(() -> new RuntimeException("Subcategory not found"));
                    review.setSubcategory(subcategory);
                    review = reviewRepository.save(review);
                    return convertToDTO(review);
                })
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    // Сортировки и фильтрация

    public Page<ReviewDTO> getReviewsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return reviewRepository.findByCreatedAtBetween(startDate, endDate, pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsBySubcategory(Long subcategoryId, Pageable pageable) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        return reviewRepository.findBySubcategory(subcategory, pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsByCategory(Long categoryId, Pageable pageable) {
        return reviewRepository.findByCategory(categoryId, pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsSortedByRatingAsc(Pageable pageable) {
        return reviewRepository.findAllByOrderByRatingAsc(pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsSortedByRatingDesc(Pageable pageable) {
        return reviewRepository.findAllByOrderByRatingAsc(pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsSortedByDateAsc(Pageable pageable) {
        return reviewRepository.findAllByOrderByCreatedAtAsc(pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsSortedByDateDesc(Pageable pageable) {
        return reviewRepository.findAllByOrderByCreatedAtDesc(pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsByDateRangeSortedByDateDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return reviewRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(startDate, endDate, pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsBySubcategorySortedByRatingDesc(Long subcategoryId, Pageable pageable) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        return reviewRepository.findBySubcategoryOrderByRatingDesc(subcategory, pageable).map(this::convertToDTO);
    }

    public Page<ReviewDTO> getReviewsByCategorySortedByDateDesc(Long categoryId, Pageable pageable) {
        return reviewRepository.findByCategoryOrderByCreatedAtDesc(categoryId, pageable).map(this::convertToDTO);
    }


    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setText(review.getText());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setSubcategoryId(review.getSubcategory().getId());
        return reviewDTO;
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setText(reviewDTO.getText());
        review.setRating(reviewDTO.getRating());
        return review;
    }
}
