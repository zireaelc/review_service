package com.promo.reviewservice;

import com.promo.reviewservice.dto.review.ReviewRequest;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {
//    @Mock
//    private ReviewRepository reviewRepository;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private SubcategoryRepository subcategoryRepository;
//
//    @Mock
//    private EmailService emailService;
//
//    @InjectMocks
//    private ReviewService reviewService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        // Установка текущего пользователя для авторизации
//        UserDetails userDetails = User.withUsername("testUser")
//                .password("password")
//                .roles("USER")
//                .build();
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.setAuthentication(authentication);
//    }
//
//    @Test
//    void testGetReviewById() {
//        // Arrange
//        Review review = new Review();
//        review.setId(UUID.fromString("1"));
//        review.setText("Review 1");
//        review.setRating(5);
//        Subcategory subcategory1 = new Subcategory();
//        subcategory1.setId(UUID.fromString("1"));
//        subcategory1.setName("Subcategory 1");
//        review.setSubcategory(subcategory1);
//
//        when(reviewRepository.findById(UUID.fromString("1"))).thenReturn(Optional.of(review));
//
//        // Act
//        Optional<ReviewRequest> reviewDTO = reviewService.getReviewById(UUID.fromString("1"));
//
//        // Assert
//        assertTrue(reviewDTO.isPresent());
//        assertEquals("Review 1", reviewDTO.get().getText());
//        assertEquals(5, reviewDTO.get().getRating());
//        assertEquals(UUID.fromString("1"), reviewDTO.get().getSubcategoryId());
//    }
//
//    @Test
//    void testGetReviewByIdNotFound() {
//        // Arrange
//        when(reviewRepository.findById(UUID.fromString("1"))).thenReturn(Optional.empty());
//
//        // Act
//        Optional<ReviewRequest> reviewDTO = reviewService.getReviewById(UUID.fromString("1"));
//
//        // Assert
//        assertFalse(reviewDTO.isPresent());
//    }
//
//    @Test
//    void testCreateReview() {
//        // Arrange
//        ReviewRequest reviewRequest = new ReviewRequest();
//        reviewRequest.setText("New Review");
//        reviewRequest.setRating(5);
//        reviewRequest.setSubcategoryId(UUID.fromString("1"));
//
//        Subcategory subcategory = new Subcategory();
//        subcategory.setId(UUID.fromString("1"));
//        subcategory.setName("Subcategory 1");
//        Category category = new Category();
//        category.setId(UUID.fromString("1"));
//        category.setName("Category 1");
//        subcategory.setCategory(category);
//
//        Review savedReview = new Review();
//        savedReview.setId(UUID.fromString("1"));
//        savedReview.setText("New Review");
//        savedReview.setRating(5);
//        savedReview.setSubcategory(subcategory);
//
//        when(subcategoryRepository.findById(UUID.fromString("1"))).thenReturn(Optional.of(subcategory));
//        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
//
//        // Act
//        ReviewRequest createdReviewRequest = reviewService.createReview(reviewRequest);
//
//        // Assert
//        assertNotNull(createdReviewRequest.getId());
//        assertEquals("New Review", createdReviewRequest.getText());
//        assertEquals(5, createdReviewRequest.getRating());
//        assertEquals(UUID.fromString("1"), createdReviewRequest.getSubcategoryId());
//    }
//
//    @Test
//    void testCreateReviewSubcategoryNotFound() {
//        // Arrange
//        ReviewRequest reviewRequest = new ReviewRequest();
//        reviewRequest.setText("New Review");
//        reviewRequest.setRating(5);
//        reviewRequest.setSubcategoryId(UUID.fromString("1"));
//
//        when(subcategoryRepository.findById(UUID.fromString("1"))).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> {
//            reviewService.createReview(reviewRequest);
//        });
//    }
//
//    @Test
//    void testUpdateReview() {
//        // Arrange
//        Review existingReview = new Review();
//        existingReview.setId(UUID.fromString("1"));
//        existingReview.setText("Old Review");
//        existingReview.setRating(4);
//        Subcategory subcategory1 = new Subcategory();
//        subcategory1.setId(UUID.fromString("1"));
//        subcategory1.setName("Subcategory 1");
//        Category category = new Category();
//        category.setId(UUID.fromString("1"));
//        category.setName("Category 1");
//        subcategory1.setCategory(category);
//        existingReview.setSubcategory(subcategory1);
//
//        ReviewRequest updatedReviewRequest = new ReviewRequest();
//        updatedReviewRequest.setText("Updated Review");
//        updatedReviewRequest.setRating(5);
//        updatedReviewRequest.setSubcategoryId(UUID.fromString("2"));
//
//        Subcategory updatedSubcategory = new Subcategory();
//        updatedSubcategory.setId(UUID.fromString("2"));
//        Category category1 = new Category();
//        category1.setId(UUID.fromString("1"));
//        category1.setName("Category 1");
//        updatedSubcategory.setCategory(category);
//        updatedSubcategory.setName("Subcategory 2");
//
//        when(reviewRepository.findById(UUID.fromString("1"))).thenReturn(Optional.of(existingReview));
//        when(subcategoryRepository.findById(UUID.fromString("2"))).thenReturn(Optional.of(updatedSubcategory));
//        when(reviewRepository.save(any(Review.class))).thenReturn(existingReview);
//
//        // Act
//        ReviewRequest resultDTO = reviewService.updateReview(UUID.fromString("1"), updatedReviewRequest);
//
//        // Assert
//        assertEquals("Updated Review", resultDTO.getText());
//        assertEquals(5, resultDTO.getRating());
//        assertEquals(UUID.fromString("2"), resultDTO.getSubcategoryId());
//    }
//
//    @Test
//    void testUpdateReviewNotFound() {
//        // Arrange
//        ReviewRequest updatedReviewRequest = new ReviewRequest();
//        updatedReviewRequest.setText("Updated Review");
//        updatedReviewRequest.setRating(5);
//        updatedReviewRequest.setSubcategoryId(UUID.fromString("2"));
//
//        when(reviewRepository.findById(UUID.fromString("1"))).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> {
//            reviewService.updateReview(UUID.fromString("1"), updatedReviewRequest);
//        });
//    }
//
//    @Test
//    void testUpdateReviewSubcategoryNotFound() {
//        // Arrange
//        Review existingReview = new Review();
//        existingReview.setId(UUID.fromString("1"));
//        existingReview.setText("Old Review");
//        existingReview.setRating(4);
//        Subcategory subcategory1 = new Subcategory();
//        subcategory1.setId(UUID.fromString("1"));
//        subcategory1.setName("Subcategory 1");
//        Category category = new Category();
//        category.setId(UUID.fromString("1"));
//        category.setName("Category 1");
//        subcategory1.setCategory(category);
//        existingReview.setSubcategory(subcategory1);
//
//        ReviewRequest updatedReviewRequest = new ReviewRequest();
//        updatedReviewRequest.setText("Updated Review");
//        updatedReviewRequest.setRating(5);
//        updatedReviewRequest.setSubcategoryId(UUID.fromString("2"));
//
//        when(reviewRepository.findById(UUID.fromString("1"))).thenReturn(Optional.of(existingReview));
//        when(subcategoryRepository.findById(UUID.fromString("2"))).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> {
//            reviewService.updateReview(UUID.fromString("1"), updatedReviewRequest);
//        });
//    }
//
//    @Test
//    void testDeleteReview() {
//        // Arrange
//        doNothing().when(reviewRepository).deleteById(UUID.fromString("1"));
//
//        // Act
//        reviewService.deleteReview(UUID.fromString("1"));
//
//        // Assert
//        verify(reviewRepository, times(1)).deleteById(UUID.fromString("1"));
//    }
}
