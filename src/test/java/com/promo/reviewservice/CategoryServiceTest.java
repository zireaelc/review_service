package com.promo.reviewservice;

import com.promo.reviewservice.dto.CategoryDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Category 2");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Act
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();

        // Assert
        assertEquals(2, categoryDTOs.size());
        assertEquals("Category 1", categoryDTOs.get(0).getName());
        assertEquals("Category 2", categoryDTOs.get(1).getName());
    }

    @Test
    void testCreateCategory() {
        // Arrange
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("New Category");

        Category savedCategory = new Category();
        savedCategory.setId(1L);
        savedCategory.setName("New Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        CategoryDTO createdCategoryDTO = categoryService.createCategory(categoryDTO);

        // Assert
        assertNotNull(createdCategoryDTO.getId());
        assertEquals("New Category", createdCategoryDTO.getName());
    }

    @Test
    void testGetCategoryById() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // Act
        Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(1L);

        // Assert
        assertTrue(categoryDTO.isPresent());
        assertEquals("Category 1", categoryDTO.get().getName());
    }

    @Test
    void testGetCategoryByIdNotFound() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(1L);

        // Assert
        assertFalse(categoryDTO.isPresent());
    }

    @Test
    void testUpdateCategory() {
        // Arrange
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Old Category");

        CategoryDTO updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setName("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        // Act
        CategoryDTO resultDTO = categoryService.updateCategory(1L, updatedCategoryDTO);

        // Assert
        assertEquals("Updated Category", resultDTO.getName());
    }

    @Test
    void testUpdateCategoryNotFound() {
        // Arrange
        CategoryDTO updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setName("Updated Category");

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(1L, updatedCategoryDTO);
        });
    }

    @Test
    void testDeleteCategory() {
        // Arrange
        doNothing().when(categoryRepository).deleteById(1L);

        // Act
        categoryService.deleteCategory(1L);

        // Assert
        verify(categoryRepository, times(1)).deleteById(1L);
    }
}
