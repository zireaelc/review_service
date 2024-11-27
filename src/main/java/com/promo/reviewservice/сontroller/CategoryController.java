package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.category.CategoryRequest;
import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.mapper.CategoryMapper;
import com.promo.reviewservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/categories")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/categories")
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryMapper.fromCategoryRequest(categoryRequest));
    }

    @GetMapping("/categories/{id}")
    public CategoryResponse getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @PutMapping("/categories/{id}")
    public CategoryResponse updateCategory(@PathVariable UUID id, @RequestBody CategoryRequest updatedCategoryRequest) {
        return categoryService.updateCategory(id, categoryMapper.fromCategoryRequest(updatedCategoryRequest));
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
