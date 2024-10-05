package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.ReviewDTO;
import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.ReviewRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final SubcategoryRepository subcategoryRepository;

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        Subcategory subcategory = subcategoryRepository.findById(reviewDTO.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
        review.setSubcategory(subcategory);
        review.setCreatedAt(LocalDateTime.now());
        review = reviewRepository.save(review);
        return convertToDTO(review);
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

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(review.getId());
        reviewDTO.setText(review.getText());
        reviewDTO.setRating(review.getRating());
        reviewDTO.setCreatedAt(review.getCreatedAt());
        reviewDTO.setSubcategoryId(review.getSubcategory().getId());
        return reviewDTO;
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setId(reviewDTO.getId());
        review.setText(reviewDTO.getText());
        review.setRating(reviewDTO.getRating());
        review.setCreatedAt(reviewDTO.getCreatedAt());
        return review;
    }
}
