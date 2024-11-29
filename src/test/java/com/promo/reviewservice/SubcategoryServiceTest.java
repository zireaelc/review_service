package com.promo.reviewservice;

import com.promo.reviewservice.dto.SubcategoryDTO;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.CategoryRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import com.promo.reviewservice.service.SubcategoryService;
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

public class SubcategoryServiceTest {

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private SubcategoryService subcategoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSubcategories() {
        // Arrange
        Subcategory subcategory1 = new Subcategory();
        subcategory1.setId(1L);
        subcategory1.setName("Subcategory 1");
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Category 1");
        subcategory1.setCategory(category1);

        Subcategory subcategory2 = new Subcategory();
        subcategory2.setId(2L);
        subcategory2.setName("Subcategory 2");
        Category category2 = new Category();
        category2.setId(1L);
        category2.setName("Category 2");
        subcategory2.setCategory(category2);

        when(subcategoryRepository.findAll()).thenReturn(Arrays.asList(subcategory1, subcategory2));

        // Act
        List<SubcategoryDTO> subcategoryDTOs = subcategoryService.getAllSubcategories();

        // Assert
        assertEquals(2, subcategoryDTOs.size());
        assertEquals("Subcategory 1", subcategoryDTOs.get(0).getName());
        assertEquals("Subcategory 2", subcategoryDTOs.get(1).getName());
    }

    @Test
    void testCreateSubcategory() {
        // Arrange
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
        subcategoryDTO.setName("New Subcategory");
        subcategoryDTO.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");

        Subcategory savedSubcategory = new Subcategory();
        savedSubcategory.setId(1L);
        savedSubcategory.setName("New Subcategory");
        savedSubcategory.setCategory(category);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(savedSubcategory);

        // Act
        SubcategoryDTO createdSubcategoryDTO = subcategoryService.createSubcategory(subcategoryDTO);

        // Assert
        assertNotNull(createdSubcategoryDTO.getId());
        assertEquals("New Subcategory", createdSubcategoryDTO.getName());
        assertEquals(1L, createdSubcategoryDTO.getCategoryId());
    }

    @Test
    void testCreateSubcategoryCategoryNotFound() {
        // Arrange
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
        subcategoryDTO.setName("New Subcategory");
        subcategoryDTO.setCategoryId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            subcategoryService.createSubcategory(subcategoryDTO);
        });
    }

    @Test
    void testGetSubcategoryById() {
        // Arrange
        Subcategory subcategory = new Subcategory();
        subcategory.setId(1L);
        subcategory.setName("Subcategory 1");
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        subcategory.setCategory(category);

        when(subcategoryRepository.findById(1L)).thenReturn(Optional.of(subcategory));

        // Act
        Optional<SubcategoryDTO> subcategoryDTO = subcategoryService.getSubcategoryById(1L);

        // Assert
        assertTrue(subcategoryDTO.isPresent());
        assertEquals("Subcategory 1", subcategoryDTO.get().getName());
        assertEquals(1L, subcategoryDTO.get().getCategoryId());
    }

    @Test
    void testGetSubcategoryByIdNotFound() {
        // Arrange
        when(subcategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<SubcategoryDTO> subcategoryDTO = subcategoryService.getSubcategoryById(1L);

        // Assert
        assertFalse(subcategoryDTO.isPresent());
    }

    @Test
    void testUpdateSubcategory() {
        // Arrange
        Subcategory existingSubcategory = new Subcategory();
        existingSubcategory.setId(1L);
        existingSubcategory.setName("Old Subcategory");
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        existingSubcategory.setCategory(category);

        SubcategoryDTO updatedSubcategoryDTO = new SubcategoryDTO();
        updatedSubcategoryDTO.setName("Updated Subcategory");
        updatedSubcategoryDTO.setCategoryId(2L);

        Category updatedCategory = new Category();
        updatedCategory.setId(2L);
        updatedCategory.setName("Category 2");

        when(subcategoryRepository.findById(1L)).thenReturn(Optional.of(existingSubcategory));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(updatedCategory));
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(existingSubcategory);

        // Act
        SubcategoryDTO resultDTO = subcategoryService.updateSubcategory(1L, updatedSubcategoryDTO);

        // Assert
        assertEquals("Updated Subcategory", resultDTO.getName());
        assertEquals(2L, resultDTO.getCategoryId());
    }

    @Test
    void testUpdateSubcategoryNotFound() {
        // Arrange
        SubcategoryDTO updatedSubcategoryDTO = new SubcategoryDTO();
        updatedSubcategoryDTO.setName("Updated Subcategory");
        updatedSubcategoryDTO.setCategoryId(2L);

        when(subcategoryRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            subcategoryService.updateSubcategory(1L, updatedSubcategoryDTO);
        });
    }

    @Test
    void testUpdateSubcategoryCategoryNotFound() {
        // Arrange
        Subcategory existingSubcategory = new Subcategory();
        existingSubcategory.setId(1L);
        existingSubcategory.setName("Old Subcategory");
        Category category = new Category();
        category.setId(1L);
        category.setName("Category 1");
        existingSubcategory.setCategory(category);

        SubcategoryDTO updatedSubcategoryDTO = new SubcategoryDTO();
        updatedSubcategoryDTO.setName("Updated Subcategory");
        updatedSubcategoryDTO.setCategoryId(2L);

        when(subcategoryRepository.findById(1L)).thenReturn(Optional.of(existingSubcategory));
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            subcategoryService.updateSubcategory(1L, updatedSubcategoryDTO);
        });
    }

    @Test
    void testDeleteSubcategory() {
        // Arrange
        doNothing().when(subcategoryRepository).deleteById(1L);

        // Act
        subcategoryService.deleteSubcategory(1L);

        // Assert
        verify(subcategoryRepository, times(1)).deleteById(1L);
    }
}
