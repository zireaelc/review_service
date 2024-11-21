package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.CategoryDTO;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        category = categoryRepository.save(category);
        return category;
    }

    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(UUID id, Category updatedCategory) {
        return categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
