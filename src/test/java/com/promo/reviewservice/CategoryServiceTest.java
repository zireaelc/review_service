package com.promo.reviewservice;

import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.repository.CategoryRepository;
import com.promo.reviewservice.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @InjectMocks
//    private CategoryService categoryService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllCategories() {
//        // Arrange
//        Category category1 = new Category();
//        UUID id1 = UUID.randomUUID();
//        category1.setId(id1);
//        category1.setName("Category 1");
//
//        Category category2 = new Category();
//        UUID id2 = UUID.randomUUID();
//        category2.setId(id2);
//        category2.setName("Category 2");
//
//        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));
//
//        // Act
//        List<Category> categories = categoryService.getAllCategories();
//
//        // Assert
//        assertEquals(2, categories.size());
//        assertEquals("Category 1", categories.get(0).getName());
//        assertEquals("Category 2", categories.get(1).getName());
//    }
//
//    @Test
//    void testCreateCategory() {
//        // Arrange
//        Category category = new Category();
//        category.setName("New Category");
//
//        Category savedCategory = new Category();
//        UUID id1 = UUID.randomUUID();
//        savedCategory.setId(id1);
//        savedCategory.setName("New Category");
//
//        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);
//
//        // Act
//        Category createdCategory = categoryService.createCategory(category);
//
//        // Assert
//        assertNotNull(createdCategory.getId());
//        assertEquals("New Category", createdCategory.getName());
//    }
//
//    @Test
//    void testGetCategoryById() {
//        // Arrange
//        Category category = new Category();
//        UUID id1 = UUID.randomUUID();
//        category.setId(id1);
//        category.setName("Category 1");
//
//        when(categoryRepository.findById(id1)).thenReturn(Optional.of(category));
//
//        // Act
//        Optional<Category> category1 = categoryService.getCategoryById(id1);
//
//        // Assert
//        assertTrue(category1.isPresent());
//        assertEquals("Category 1", category1.get().getName());
//    }
//
//    @Test
//    void testGetCategoryByIdNotFound() {
//        // Arrange
//        UUID id1 = UUID.randomUUID();
//        when(categoryRepository.findById(id1).orElseThrow());
//
//        // Act
//        Optional<Category> category = categoryService.getCategoryById(id1);
//
//        // Assert
//        assertFalse(category.isPresent());
//    }
//
//    @Test
//    void testUpdateCategory() {
//        // Arrange
//        Category existingCategory = new Category();
//        UUID id1 = UUID.randomUUID();
//        existingCategory.setId(id1);
//        existingCategory.setName("Old Category");
//
//        Category updatedCategory = new Category();
//        updatedCategory.setName("Updated Category");
//
//        when(categoryRepository.findById(id1)).thenReturn(Optional.of(existingCategory));
//        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);
//
//        // Act
//        Category result = categoryService.updateCategory(id1, updatedCategory);
//
//        // Assert
//        assertEquals("Updated Category", result.getName());
//    }
//
//    @Test
//    void testUpdateCategoryNotFound() {
//        // Arrange
//        Category updatedCategory = new Category();
//        updatedCategory.setName("Updated Category");
//
//        UUID id1 = UUID.randomUUID();
//        when(categoryRepository.findById(id1)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> {
//            categoryService.updateCategory(id1, updatedCategory);
//        });
//    }
//
//    @Test
//    void testDeleteCategory() {
//        UUID id1 = UUID.randomUUID();
//        // Arrange
//        doNothing().when(categoryRepository).deleteById(id1);
//
//        // Act
//        categoryService.deleteCategory(id1);
//
//        // Assert
//        verify(categoryRepository, times(1)).deleteById(id1);
//    }
}
