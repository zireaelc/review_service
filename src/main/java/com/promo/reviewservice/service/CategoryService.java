package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.mapper.CategoryMapper;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toCategoryResponseList(categoryRepository.findAll());
    }

    public CategoryResponse createCategory(Category category) {
        category = categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    public Optional<CategoryResponse> getCategoryById(UUID id) {
        return categoryMapper.toCategoryResponseOptional(categoryRepository.findById(id));
    }

    public CategoryResponse updateCategory(UUID id, Category updatedCategory) {
        var result = categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toCategoryResponse(result);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
