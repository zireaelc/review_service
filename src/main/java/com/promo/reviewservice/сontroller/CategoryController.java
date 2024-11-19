package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.CategoryDTO;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    private CategoryDTO toDto(Category entity) {
        return modelMapper.map(entity, CategoryDTO.class);
    }

    private Category toEntity(CategoryDTO dto) {
        return modelMapper.map(dto, Category.class);
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategories().stream()
                .map(this::toDto)
                .toList();
    }

    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return toDto(categoryService.createCategory(toEntity(categoryDTO)));
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO updatedCategoryDTO) {
        return toDto(categoryService.updateCategory(id, toEntity(updatedCategoryDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }
}
