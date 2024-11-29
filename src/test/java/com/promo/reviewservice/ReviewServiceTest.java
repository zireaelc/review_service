package com.promo.reviewservice;

import com.promo.reviewservice.dto.ReviewDTO;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.ReviewRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import com.promo.reviewservice.service.EmailService;
import com.promo.reviewservice.service.ReviewService;
import com.promo.reviewservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserService userService;

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Установка текущего пользователя для авторизации
        UserDetails userDetails = User.withUsername("testUser")
                .password("password")
                .roles("USER")
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    @Test
    void testGetReviewById() {
        // Arrange
        Review review = new Review();
        review.setId(1L);
        review.setText("Review 1");
        review.setRating(5);
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setId(1L);
        subcategory1.setName("Subcategory 1");
        review.setSubcategory(subcategory1);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // Act
        Optional<ReviewDTO> reviewDTO = reviewService.getReviewById(1L);

        // Assert
        assertTrue(reviewDTO.isPresent());
        assertEquals("Review 1", reviewDTO.get().getText());
        assertEquals(5, reviewDTO.get().getRating());
        assertEquals(1L, reviewDTO.get().getSubcategoryId());
    }

    @Test
    void testGetReviewByIdNotFound() {
        // Arrange
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<ReviewDTO> reviewDTO = reviewService.getReviewById(1L);

        // Assert
        assertFalse(reviewDTO.isPresent());
    }

    @Test
    void testCreateReview() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText("New Review");
        reviewDTO.setRating(5);
        reviewDTO.setSubcategoryId(1L);

        Subcategory subcategory = new Subcategory();
        subcategory.setId(1L);
        subcategory.setName("Subcategory 1");
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        subcategory.setCategory(category);

        Review savedReview = new Review();
        savedReview.setId(1L);
        savedReview.setText("New Review");
        savedReview.setRating(5);
        savedReview.setSubcategory(subcategory);

        when(subcategoryRepository.findById(1L)).thenReturn(Optional.of(subcategory));
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // Act
        ReviewDTO createdReviewDTO = reviewService.createReview(reviewDTO);

        // Assert
        assertNotNull(createdReviewDTO.getId());
        assertEquals("New Review", createdReviewDTO.getText());
        assertEquals(5, createdReviewDTO.getRating());
        assertEquals(1L, createdReviewDTO.getSubcategoryId());
    }

    @Test
    void testCreateReviewSubcategoryNotFound() {
        // Arrange
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setText("New Review");
        reviewDTO.setRating(5);
        reviewDTO.setSubcategoryId(1L);

        when(subcategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reviewService.createReview(reviewDTO);
        });
    }

    @Test
    void testUpdateReview() {
        // Arrange
        Review existingReview = new Review();
        existingReview.setId(1L);
        existingReview.setText("Old Review");
        existingReview.setRating(4);
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setId(1L);
        subcategory1.setName("Subcategory 1");
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        subcategory1.setCategory(category);
        existingReview.setSubcategory(subcategory1);

        ReviewDTO updatedReviewDTO = new ReviewDTO();
        updatedReviewDTO.setText("Updated Review");
        updatedReviewDTO.setRating(5);
        updatedReviewDTO.setSubcategoryId(2L);

        Subcategory updatedSubcategory = new Subcategory();
        updatedSubcategory.setId(2L);
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");
        updatedSubcategory.setCategory(category);
        updatedSubcategory.setName("Subcategory 2");

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));
        when(subcategoryRepository.findById(2L)).thenReturn(Optional.of(updatedSubcategory));
        when(reviewRepository.save(any(Review.class))).thenReturn(existingReview);

        // Act
        ReviewDTO resultDTO = reviewService.updateReview(1L, updatedReviewDTO);

        // Assert
        assertEquals("Updated Review", resultDTO.getText());
        assertEquals(5, resultDTO.getRating());
        assertEquals(2L, resultDTO.getSubcategoryId());
    }

    @Test
    void testUpdateReviewNotFound() {
        // Arrange
        ReviewDTO updatedReviewDTO = new ReviewDTO();
        updatedReviewDTO.setText("Updated Review");
        updatedReviewDTO.setRating(5);
        updatedReviewDTO.setSubcategoryId(2L);

        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reviewService.updateReview(1L, updatedReviewDTO);
        });
    }

    @Test
    void testUpdateReviewSubcategoryNotFound() {
        // Arrange
        Review existingReview = new Review();
        existingReview.setId(1L);
        existingReview.setText("Old Review");
        existingReview.setRating(4);
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setId(1L);
        subcategory1.setName("Subcategory 1");
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        subcategory1.setCategory(category);
        existingReview.setSubcategory(subcategory1);

        ReviewDTO updatedReviewDTO = new ReviewDTO();
        updatedReviewDTO.setText("Updated Review");
        updatedReviewDTO.setRating(5);
        updatedReviewDTO.setSubcategoryId(2L);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(existingReview));
        when(subcategoryRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reviewService.updateReview(1L, updatedReviewDTO);
        });
    }

    @Test
    void testDeleteReview() {
        // Arrange
        doNothing().when(reviewRepository).deleteById(1L);

        // Act
        reviewService.deleteReview(1L);

        // Assert
        verify(reviewRepository, times(1)).deleteById(1L);
    }
}
