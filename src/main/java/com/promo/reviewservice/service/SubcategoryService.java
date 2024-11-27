package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.mapper.SubcategoryMapper;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.CategoryRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubcategoryMapper subcategoryMapper;

    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryMapper.toSubcategoryResponseList(subcategoryRepository.findAll());
    }

    public SubcategoryResponse createSubcategory(Subcategory subcategory) {
        Category category = getCategoryById(subcategory.getCategory().getId());
        subcategory.setCategory(category);
        return subcategoryMapper.toSubcategoryResponse(subcategoryRepository.save(subcategory));
    }

    public Optional<SubcategoryResponse> getSubcategoryById(UUID id) {
        return Optional.of(subcategoryMapper.toSubcategoryResponse(subcategoryRepository.findById(id).get()));
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
        return subcategoryMapper.toSubcategoryResponse(result);
    }

    public void deleteSubcategory(UUID id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subcategory not found with id: " + id);
        }
        subcategoryRepository.deleteById(id);
    }

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
    }
}
