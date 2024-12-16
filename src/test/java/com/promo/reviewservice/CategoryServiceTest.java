package com.promo.reviewservice;

import com.promo.reviewservice.dto.category.CategoryRequest;
import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.mapper.CategoryRequestMapper;
import com.promo.reviewservice.mapper.CategoryResponseMapper;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.repository.CategoryRepository;
import com.promo.reviewservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryResponse categoryResponse;
    private CategoryRequest categoryRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Test Category");
        categoryResponse = new CategoryResponse(category.getId().toString(), category.getName());
        categoryRequest = new CategoryRequest("Test Category");
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(categoryResponseMapper.map(any(Category.class))).thenReturn(categoryResponse);

        // Act
        List<CategoryResponse> result = categoryService.getAllCategories();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(categoryResponse, result.get(0));
    }

    @Test
    void testCreateCategory() {
        // Arrange
        when(categoryRequestMapper.map(any(CategoryRequest.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryResponseMapper.map(any(Category.class))).thenReturn(categoryResponse);

        // Act
        CategoryResponse result = categoryService
                .createCategory(categoryRequestMapper.map(categoryRequest));

        // Assert
        assertNotNull(result);
        assertEquals(categoryResponse, result);
    }

    @Test
    void testGetCategoryById() {
        // Arrange
        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(category));
        when(categoryResponseMapper.map(any(Category.class))).thenReturn(categoryResponse);

        // Act
        Optional<CategoryResponse> result = categoryService.getCategoryById(category.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(categoryResponse, result.get());
    }

    @Test
    void testGetCategoryByIdNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(categoryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> categoryService.getCategoryById(nonExistentId));
    }

    @Test
    void testUpdateCategory() {
        // Arrange
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category");

        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        when(categoryResponseMapper.map(any(Category.class))).thenReturn(
                new CategoryResponse(category.getId().toString(), "Updated Category"));

        // Act
        CategoryResponse result = categoryService
                .updateCategory(category.getId(), updatedCategory);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Category", result.name());
    }

    @Test
    void testUpdateCategoryNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Category");

        when(categoryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> categoryService.updateCategory(nonExistentId, updatedCategory));
    }

    @Test
    void testDeleteCategory() {
        // Arrange
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> categoryService.deleteCategory(category.getId()));

        // Assert
        assertDoesNotThrow(() -> categoryService.deleteCategory(category.getId()));
    }

    @Test
    public void testDeleteCategoryNotFound() {
        // Arrange
        when(categoryRepository.existsById(any(UUID.class))).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> categoryService.deleteCategory(category.getId()));
    }
}
