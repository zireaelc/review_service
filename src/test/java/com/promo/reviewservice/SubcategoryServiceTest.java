package com.promo.reviewservice;

import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.mapper.SubcategoryRequestMapper;
import com.promo.reviewservice.mapper.SubcategoryResponseMapper;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubcategoryServiceTest {

    @Mock
    private SubcategoryRepository subcategoryRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SubcategoryResponseMapper subcategoryResponseMapper;

    @Mock
    private SubcategoryRequestMapper subcategoryRequestMapper;

    @InjectMocks
    private SubcategoryService subcategoryService;

    private Subcategory subcategory;
    private SubcategoryResponse subcategoryResponse;
    private SubcategoryRequest subcategoryRequest;
    private Category category;

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

        subcategoryResponse = new SubcategoryResponse(
                subcategory.getId().toString(),
                subcategory.getName(),
                category.getId().toString());
        subcategoryRequest = new SubcategoryRequest(subcategory.getName(), category.getId().toString());
    }

    @Test
    void testGetAllSubcategories() {
        // Arrange
        when(subcategoryRepository.findAll()).thenReturn(List.of(subcategory));
        when(subcategoryResponseMapper.map(any(Subcategory.class))).thenReturn(subcategoryResponse);

        // Act
        List<SubcategoryResponse> result = subcategoryService.getAllSubcategories();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(subcategoryResponse, result.get(0));
    }

    @Test
    void testCreateSubcategory() {
        // Arrange
        when(subcategoryRequestMapper.map(any(SubcategoryRequest.class))).thenReturn(subcategory);
        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(category));
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(subcategory);
        when(subcategoryResponseMapper.map(any(Subcategory.class))).thenReturn(subcategoryResponse);

        // Act
        SubcategoryResponse result = subcategoryService
                .createSubcategory(subcategoryRequestMapper.map(subcategoryRequest));

        // Assert
        assertNotNull(result);
        assertEquals(subcategoryResponse, result);
    }

    @Test
    void testCreateSubcategoryCategoryNotFound() {
        // Arrange
        UUID nonExistentCategoryId = UUID.randomUUID();
        subcategoryRequest = new SubcategoryRequest("Test Subcategory", nonExistentCategoryId.toString());

        when(subcategoryRequestMapper.map(any(SubcategoryRequest.class))).thenReturn(subcategory);
        when(categoryRepository.findById(nonExistentCategoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> subcategoryService.createSubcategory(subcategoryRequestMapper.map(subcategoryRequest)));
    }

    @Test
    void testGetSubcategoryById() {
        // Arrange
        when(subcategoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(subcategory));
        when(subcategoryResponseMapper.map(any(Subcategory.class))).thenReturn(subcategoryResponse);

        // Act
        Optional<SubcategoryResponse> result = subcategoryService.getSubcategoryById(subcategory.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(subcategoryResponse, result.get());
    }

    @Test
    void testGetSubcategoryByIdNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(subcategoryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> subcategoryService.getSubcategoryById(nonExistentId));
    }

    @Test
    void testUpdateSubcategory() {
        // Arrange
        Subcategory updatedSubcategory = new Subcategory();
        updatedSubcategory.setName("Updated Subcategory");
        updatedSubcategory.setCategory(category);

        when(subcategoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(subcategory));
        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(category));
        when(subcategoryRepository.save(any(Subcategory.class))).thenReturn(updatedSubcategory);
        when(subcategoryResponseMapper.map(any(Subcategory.class)))
                .thenReturn(new SubcategoryResponse(
                        subcategory.getId().toString(),
                        "Updated Subcategory",
                        category.getId().toString()));

        // Act
        SubcategoryResponse result = subcategoryService.updateSubcategory(subcategory.getId(), updatedSubcategory);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Subcategory", result.name());
    }

    @Test
    void testUpdateSubcategoryNotFound() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        Subcategory updatedSubcategory = new Subcategory();
        updatedSubcategory.setName("Updated Subcategory");
        updatedSubcategory.setCategory(category);

        when(subcategoryRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> subcategoryService.updateSubcategory(nonExistentId, updatedSubcategory));
    }

    @Test
    void testUpdateSubcategoryCategoryNotFound() {
        // Arrange
        UUID nonExistentCategoryId = UUID.randomUUID();
        Subcategory updatedSubcategory = new Subcategory();
        updatedSubcategory.setName("Updated Subcategory");
        updatedSubcategory.setCategory(new Category());
        updatedSubcategory.getCategory().setId(nonExistentCategoryId);

        when(subcategoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(subcategory));
        when(categoryRepository.findById(nonExistentCategoryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> subcategoryService.updateSubcategory(subcategory.getId(), updatedSubcategory));
    }

    @Test
    void testDeleteSubcategory() {
        // Arrange
        when(subcategoryRepository.existsById(any(UUID.class))).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> subcategoryService.deleteSubcategory(subcategory.getId()));

        // Assert
        verify(subcategoryRepository, times(1)).deleteById(subcategory.getId());
    }

    @Test
    public void testDeleteSubcategoryNotFound() {
        // Arrange
        when(subcategoryRepository.existsById(any(UUID.class))).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class,
                () -> subcategoryService.deleteSubcategory(subcategory.getId()));
    }
}
