package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.mapper.CategoryResponseMapper;
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
    private final CategoryResponseMapper categoryMapper;

    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.map(categoryRepository.findAll());
    }

    public CategoryResponse createCategory(Category category) {
        category = categoryRepository.save(category);
        return categoryMapper.map(category);
    }

    public Optional<CategoryResponse> getCategoryById(UUID id) {
        return Optional.of(categoryMapper.map(categoryRepository.findById(id).get()));
    }

    public CategoryResponse updateCategory(UUID id, Category updatedCategory) {
        var result = categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.map(result);
    }

    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
