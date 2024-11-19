package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.SubcategoryDTO;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.CategoryRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public List<Subcategory> getAllSubcategories() {
        return subcategoryRepository.findAll();
    }

    public Subcategory createSubcategory(Subcategory subcategory) {
        Category category = getCategoryById(subcategory.getCategory().getId());
        subcategory.setCategory(category);
        return subcategoryRepository.save(subcategory);
    }

    public Optional<Subcategory> getSubcategoryById(UUID id) {
        return subcategoryRepository.findById(id);
    }

    public Subcategory updateSubcategory(UUID id, Subcategory updatedSubcategory) {
        return subcategoryRepository.findById(id)
                .map(subcategory -> {
                    subcategory.setName(updatedSubcategory.getName());
                    Category category = getCategoryById(updatedSubcategory.getCategory().getId());
                    subcategory.setCategory(category);
                    return subcategoryRepository.save(subcategory);
                })
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    public void deleteSubcategory(UUID id) {
        subcategoryRepository.deleteById(id);
    }

    private Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
