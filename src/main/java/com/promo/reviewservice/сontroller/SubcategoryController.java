package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.mapper.SubcategoryRequestMapper;
import com.promo.reviewservice.service.SubcategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Подкатегории")
@RestController
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private final SubcategoryRequestMapper subcategoryMapper;

    @GetMapping("/api/v1/subcategories")
    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

    @PostMapping("/api/v1/subcategories")
    public SubcategoryResponse createSubcategory(@RequestBody SubcategoryRequest subcategoryRequest) {
        return subcategoryService.createSubcategory(subcategoryMapper.map(subcategoryRequest));
    }

    @GetMapping("/api/v1/subcategories/{subcategoryId}")
    public SubcategoryResponse getSubcategoryById(@PathVariable UUID subcategoryId) {
        return subcategoryService.getSubcategoryById(subcategoryId)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    @PutMapping("/api/v1/subcategories/{subcategoryId}")
    public SubcategoryResponse updateSubcategory(@PathVariable UUID subcategoryId,
                                                 @RequestBody SubcategoryRequest updatedSubcategoryRequest) {
        return subcategoryService.updateSubcategory(subcategoryId, subcategoryMapper.map(updatedSubcategoryRequest));
    }

    @DeleteMapping("/api/v1/subcategories/{subcategoryId}")
    public void deleteSubcategory(@PathVariable UUID subcategoryId) {
        subcategoryService.deleteSubcategory(subcategoryId);
    }
}
