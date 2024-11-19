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
import java.util.UUID;

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
        //  todo сделай норм uuid
        category1.setId(UUID.fromString("1"));
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setId(UUID.fromString("2"));
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
        savedCategory.setId(UUID.fromString("1"));
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
        category.setId(UUID.fromString("1"));
        category.setName("Category 1");

        when(categoryRepository.findById(UUID.fromString("1"))).thenReturn(Optional.of(category));

        // Act
        Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(UUID.fromString("1"));

        // Assert
        assertTrue(categoryDTO.isPresent());
        assertEquals("Category 1", categoryDTO.get().getName());
    }

    @Test
    void testGetCategoryByIdNotFound() {
        // Arrange
        // подумай емае
        // when(categoryRepository.findById((UUID.fromString("1"))).orElseThrow();

        // Act
        Optional<CategoryDTO> categoryDTO = categoryService.getCategoryById(UUID.fromString("1"));

        // Assert
        assertFalse(categoryDTO.isPresent());
    }

    @Test
    void testUpdateCategory() {
        // Arrange
        Category existingCategory = new Category();
        existingCategory.setId(UUID.fromString("1"));
        existingCategory.setName("Old Category");

        CategoryDTO updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setName("Updated Category");

        when(categoryRepository.findById(UUID.fromString("1"))).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        // Act
        CategoryDTO resultDTO = categoryService.updateCategory(UUID.fromString("1"), updatedCategoryDTO);

        // Assert
        assertEquals("Updated Category", resultDTO.getName());
    }

    @Test
    void testUpdateCategoryNotFound() {
        // Arrange
        CategoryDTO updatedCategoryDTO = new CategoryDTO();
        updatedCategoryDTO.setName("Updated Category");

        when(categoryRepository.findById(UUID.fromString("1"))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(UUID.fromString("1"), updatedCategoryDTO);
        });
    }

    @Test
    void testDeleteCategory() {
        // Arrange
        doNothing().when(categoryRepository).deleteById(UUID.fromString("1"));

        // Act
        categoryService.deleteCategory(UUID.fromString("1"));

        // Assert
        verify(categoryRepository, times(1)).deleteById(UUID.fromString("1"));
    }
}
