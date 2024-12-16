package com.promo.reviewservice;

import com.promo.reviewservice.dto.review.ReviewRequest;
import com.promo.reviewservice.dto.review.ReviewResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.mapper.ReviewRequestMapper;
import com.promo.reviewservice.mapper.ReviewResponseMapper;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.ReviewRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import com.promo.reviewservice.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private ReviewResponseMapper reviewResponseMapper;

    @Mock
    private ReviewRequestMapper reviewRequestMapper;

    private Review review;
    private ReviewResponse reviewResponse;
    private ReviewRequest reviewRequest;
    private Subcategory subcategory;
    private Category category;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Test Category");

        subcategory = new Subcategory();
        subcategory.setId(UUID.randomUUID());
        subcategory.setName("Test Subcategory");
        subcategory.setCategory(category);

        review = new Review();
        review.setId(UUID.randomUUID());
        review.setText("Test Review");
        review.setRating(5);
        review.setSubcategory(subcategory);

        reviewResponse = new ReviewResponse(
                review.getId().toString(),
                review.getText(),
                review.getRating(),
                subcategory.getId().toString());
        reviewRequest = new ReviewRequest(
                review.getText(),
                review.getRating(),
                subcategory.getId().toString());
    }

    @Test
    public void testGetAllReviews() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        when(reviewRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(review)));
        when(reviewResponseMapper.map(any(Review.class))).thenReturn(reviewResponse);

        // Act
        Page<ReviewResponse> result = reviewService.getAllReviews(pageable);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        assertEquals(reviewResponse, result.getContent().get(0));
    }

    @Test
    void testGetReviewById() {
        // Arrange
        when(reviewRepository.findById(any(UUID.class))).thenReturn(Optional.of(review));
        when(reviewResponseMapper.map(any(Review.class))).thenReturn(reviewResponse);

        // Act
        Optional<ReviewResponse> result = reviewService.getReviewById(review.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(reviewResponse, result.get());
    }

    @Test
    void testGetReviewByIdNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(reviewRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.getReviewById(nonExistentId));
    }

    @Test
    void testCreateReview() {
        // Arrange
        when(reviewRequestMapper.map(any(ReviewRequest.class))).thenReturn(review);
        when(subcategoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(subcategory));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewResponseMapper.map(any(Review.class))).thenReturn(reviewResponse);

        // Настройка SecurityContext для тестирования getCurrentUsername
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        ReviewResponse result = reviewService.createReview(reviewRequestMapper.map(reviewRequest));

        // Assert
        assertNotNull(result);
        assertEquals(reviewResponse, result);
    }

    @Test
    void testCreateReviewSubcategoryNotFound() {
        // Arrange
        when(reviewRequestMapper.map(any(ReviewRequest.class))).thenReturn(review);
        when(subcategoryRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.createReview(reviewRequestMapper.map(reviewRequest)));
    }

    @Test
    void testUpdateReview() {
        // Arrange
        Review updatedReview = new Review();
        updatedReview.setText("Updated Review");
        updatedReview.setRating(4);
        updatedReview.setSubcategory(subcategory);

        when(reviewRepository.findById(any(UUID.class))).thenReturn(Optional.of(review));
        when(subcategoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(subcategory));
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);
        when(reviewResponseMapper.map(any(Review.class))).thenReturn(
                new ReviewResponse(
                        review.getId().toString(),
                        "Updated Review",
                        4,
                        subcategory.getId().toString()));

        // Act
        ReviewResponse result = reviewService.updateReview(review.getId(), updatedReview);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Review", result.text());
        assertEquals(4, result.rating());
    }

    @Test
    void testUpdateReviewNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        Review updatedReview = new Review();
        updatedReview.setText("Updated Review");
        updatedReview.setRating(4);
        updatedReview.setSubcategory(subcategory);

        when(reviewRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.updateReview(nonExistentId, updatedReview));
    }

    @Test
    void testUpdateReviewSubcategoryNotFound() {
        // Arrange
        Review updatedReview = new Review();
        updatedReview.setText("Updated Review");
        updatedReview.setRating(4);
        updatedReview.setSubcategory(new Subcategory());
        updatedReview.getSubcategory().setId(UUID.randomUUID());

        when(reviewRepository.findById(any(UUID.class))).thenReturn(Optional.of(review));
        when(subcategoryRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.updateReview(review.getId(), updatedReview));
    }

    @Test
    void testDeleteReview() {
        // Arrange
        when(reviewRepository.existsById(any(UUID.class))).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> reviewService.deleteReview(review.getId()));

        // Assert
        verify(reviewRepository, times(1)).deleteById(review.getId());
    }

    @Test
    public void testDeleteReviewNotFound() {
        // Arrange
        when(reviewRepository.existsById(any(UUID.class))).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> reviewService.deleteReview(review.getId()));
    }
}
