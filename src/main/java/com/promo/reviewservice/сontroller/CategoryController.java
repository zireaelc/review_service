package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.category.CategoryRequest;
import com.promo.reviewservice.dto.category.CategoryResponse;
import com.promo.reviewservice.mapper.CategoryMapper;
import com.promo.reviewservice.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Категории")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/api/v1/categories")
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/api/v1/categories")
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryMapper.fromCategoryRequest(categoryRequest));
    }

    @GetMapping("/api/v1/categories/{categoryId}")
    public CategoryResponse getCategoryById(@PathVariable UUID categoryId) {
        return categoryService.getCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @PutMapping("/api/v1/categories/{categoryId}")
    public CategoryResponse updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryRequest updatedCategoryRequest) {
        return categoryService.updateCategory(categoryId, categoryMapper.fromCategoryRequest(updatedCategoryRequest));
    }

    @DeleteMapping("/api/v1/categories/{categoryId}")
    public void deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
