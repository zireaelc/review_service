package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.category.CategoryRequest;
import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.exeptions.ResourceNotFoundException;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    private CategoryResponse toDto(Category entity) {
        return modelMapper.map(entity, CategoryResponse.class);
    }

    private Category toEntity(CategoryResponse dto) {
        return modelMapper.map(dto, Category.class);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toDto).toList();
    }

    public CategoryResponse createCategory(Category category) {
        category = categoryRepository.save(category);
        return toDto(category);
    }

    public Optional<CategoryResponse> getCategoryById(UUID id) {
        return categoryRepository.findById(id).map(this::toDto);
    }

    public CategoryResponse updateCategory(UUID id, Category updatedCategory) {
        var result = categoryRepository.findById(id)
                .map(category -> {
                    category.setName(updatedCategory.getName());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return toDto(result);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
