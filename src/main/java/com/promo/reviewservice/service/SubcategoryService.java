package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.CategoryRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private SubcategoryResponse toDto(Subcategory entity) {
        return modelMapper.map(entity, SubcategoryResponse.class);
    }

    private Subcategory toEntity(SubcategoryResponse dto) {
        return modelMapper.map(dto, Subcategory.class);
    }

    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryRepository.findAll().stream().map(this::toDto).toList();
    }

    public SubcategoryResponse createSubcategory(Subcategory subcategory) {
        Category category = getCategoryById(subcategory.getCategory().getId());
        subcategory.setCategory(category);
        return toDto(subcategoryRepository.save(subcategory));
    }

    public Optional<SubcategoryResponse> getSubcategoryById(UUID id) {
        return subcategoryRepository.findById(id).map(this::toDto);
    }

    public SubcategoryResponse updateSubcategory(UUID id, Subcategory updatedSubcategory) {
        var result = subcategoryRepository.findById(id)
                .map(subcategory -> {
                    subcategory.setName(updatedSubcategory.getName());
                    Category category = getCategoryById(updatedSubcategory.getCategory().getId());
                    subcategory.setCategory(category);
                    return subcategoryRepository.save(subcategory);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with id: " + id));
        return toDto(result);
    }

    public void deleteSubcategory(UUID id) {
        subcategoryRepository.deleteById(id);
    }

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }
}
