package com.promo.reviewservice;

import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubcategoryServiceTest {
//
//    @Mock
//    private SubcategoryRepository subcategoryRepository;
//
//    @Mock
//    private CategoryRepository categoryRepository;
//
//    @InjectMocks
//    private SubcategoryService subcategoryService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetAllSubcategories() {
//        // Arrange
//        UUID id1 = UUID.randomUUID();
//        Subcategory subcategory1 = new Subcategory();
//        subcategory1.setId(id1);
//        subcategory1.setName("Subcategory 1");
//        UUID id2 = UUID.randomUUID();
//        Category category1 = new Category();
//        category1.setId(id2);
//        category1.setName("Category 1");
//        subcategory1.setCategory(category1);
//
//        UUID id3 = UUID.randomUUID();
//        Subcategory subcategory2 = new Subcategory();
//        subcategory2.setId(id3);
//        subcategory2.setName("Subcategory 2");
//        UUID id4 = UUID.randomUUID();
//        Category category2 = new Category();
//        category2.setId(id4);
//        category2.setName("Category 2");
//        subcategory2.setCategory(category2);
//
//        when(subcategoryRepository.findAll()).thenReturn(Arrays.asList(subcategory1, subcategory2));
//
//        // Act
//        List<Subcategory> subcategories = subcategoryService.getAllSubcategories();
//
//        // Assert
//        assertEquals(2, subcategories.size());
//        assertEquals("Subcategory 1", subcategories.get(0).getName());
//        assertEquals("Subcategory 2", subcategories.get(1).getName());
//    }
//
//    @Test
//    void testCreateSubcategory() {
//        // Arrange
//        UUID id2 = UUID.randomUUID();
//        Category category = new Category();
//        category.setId(id2);
//        category.setName("Category 1");
//
//        UUID id1 = UUID.randomUUID();
//        Subcategory subcategory = new Subcategory();
//        subcategory.setName("New Subcategory");
//        subcategory.setCategory(category);
//
//        UUID id3 = UUID.randomUUID();
//        Subcategory savedSubcategory = new Subcategory();
//        savedSubcategory.setId(id3);
//        savedSubcategory.setName("New Subcategory");
//        savedSubcategory.setCategory(category);
//
//        when(categoryRepository.findById(id2)).thenReturn(Optional.of(category));
//        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(savedSubcategory);
//
//        // Act
//        Subcategory createdSubcategory = subcategoryService.createSubcategory(subcategory);
//
//        // Assert
//        assertNotNull(createdSubcategory.getId());
//        assertEquals("New Subcategory", createdSubcategory.getName());
//        assertEquals(id2, createdSubcategory.getCategory().getId());
//    }
//
//    @Test
//    void testCreateSubcategoryCategoryNotFound() {
//        // Arrange
//        UUID id1 = UUID.randomUUID();
//        Subcategory subcategory = new Subcategory();
//        subcategory.setName("New Subcategory");
//        subcategory.setCategory(null);
//
//        when(categoryRepository.findById(id1)).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> {
//            subcategoryService.createSubcategory(subcategory);
//        });
//    }
//
//    @Test
//    void testGetSubcategoryById() {
//        // Arrange
//        UUID id1 = UUID.randomUUID();
//        Subcategory subcategory = new Subcategory();
//        subcategory.setId(id1);
//        subcategory.setName("Subcategory 1");
//        UUID id2 = UUID.randomUUID();
//        Category category = new Category();
//        category.setId(id2);
//        category.setName("Category 1");
//        subcategory.setCategory(category);
//
//        when(subcategoryRepository.findById(id1)).thenReturn(Optional.of(subcategory));
//
//        // Act
//        Optional<Subcategory> subcategorys = subcategoryService.getSubcategoryById(id1);
//
//        // Assert
//        assertTrue(subcategorys.isPresent());
//        assertEquals("Subcategory 1", subcategorys.get().getName());
//        assertEquals(1L, subcategorys.get().getCategory().getId());
//    }
//
//    @Test
//    void testGetSubcategoryByIdNotFound() {
//        // Arrange
//        UUID id1 = UUID.randomUUID();
//        when(subcategoryRepository.findById(id1)).thenReturn(Optional.empty());
//
//        // Act
//        Optional<Subcategory> subcategory = subcategoryService.getSubcategoryById(id1);
//
//        // Assert
//        assertFalse(subcategory.isPresent());
//    }
//
//    @Test
//    void testUpdateSubcategory() {
//        // Arrange
//        UUID id1 = UUID.randomUUID();
//        Subcategory existingSubcategory = new Subcategory();
//        existingSubcategory.setId(id1);
//        existingSubcategory.setName("Old Subcategory");
//        UUID id2 = UUID.randomUUID();
//        Category category = new Category();
//        category.setId(id2);
//        category.setName("Category 1");
//        existingSubcategory.setCategory(category);
//
//        UUID id3 = UUID.randomUUID();
//        SubcategoryRequest updatedSubcategoryRequest = new SubcategoryRequest();
//        updatedSubcategoryRequest.setName("Updated Subcategory");
//        updatedSubcategoryRequest.setCategoryId(id3);
//
//        UUID id4 = UUID.randomUUID();
//        Category updatedCategory = new Category();
//        updatedCategory.setId(id4);
//        updatedCategory.setName("Category 2");
//
//        when(subcategoryRepository.findById(UUID.fromString("1"))).thenReturn(Optional.of(existingSubcategory));
//        when(categoryRepository.findById(UUID.fromString("2"))).thenReturn(Optional.of(updatedCategory));
//        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(existingSubcategory);
//
//        // Act
//        Subcategory result = subcategoryService.updateSubcategory(UUID.fromString("1"), updatedSubcategory);
//
//        // Assert
//        assertEquals("Updated Subcategory", resultDTO.getName());
//        assertEquals(2L, resultDTO.getCategoryId());
//    }
//
//    @Test
//    void testUpdateSubcategoryNotFound() {
//        // Arrange
//        SubcategoryRequest updatedSubcategoryRequest = new SubcategoryRequest();
//        updatedSubcategoryRequest.setName("Updated Subcategory");
//        updatedSubcategoryRequest.setCategoryId(UUID.fromString("2"));
//
//        when(subcategoryRepository.findById(UUID.fromString("1"))).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> {
//            subcategoryService.updateSubcategory(UUID.fromString("1"), updatedSubcategoryRequest);
//        });
//    }
//
//    @Test
//    void testUpdateSubcategoryCategoryNotFound() {
//        // Arrange
//        Subcategory existingSubcategory = new Subcategory();
//        existingSubcategory.setId(UUID.fromString("1"));
//        existingSubcategory.setName("Old Subcategory");
//        Category category = new Category();
//        category.setId(UUID.fromString("1"));
//        category.setName("Category 1");
//        existingSubcategory.setCategory(category);
//
//        SubcategoryRequest updatedSubcategoryRequest = new SubcategoryRequest();
//        updatedSubcategoryRequest.setName("Updated Subcategory");
//        updatedSubcategoryRequest.setCategoryId(UUID.fromString("2"));
//
//        when(subcategoryRepository.findById(UUID.fromString("1"))).thenReturn(Optional.of(existingSubcategory));
//        when(categoryRepository.findById(UUID.fromString("2"))).thenReturn(Optional.empty());
//
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> {
//            subcategoryService.updateSubcategory(UUID.fromString("1"), updatedSubcategoryRequest);
//        });
//    }
//
//    @Test
//    void testDeleteSubcategory() {
//        // Arrange
//        doNothing().when(subcategoryRepository).deleteById(UUID.fromString("1"));
//
//        // Act
//        subcategoryService.deleteSubcategory(UUID.fromString("1"));
//
//        // Assert
//        verify(subcategoryRepository, times(1)).deleteById(UUID.fromString("1"));
//    }
}
