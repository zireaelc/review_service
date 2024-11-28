package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.mapper.SubcategoryMapper;
import com.promo.reviewservice.service.SubcategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Subcategory")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private final SubcategoryMapper subcategoryMapper;

    @GetMapping("/subcategories")
    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

    @PostMapping("/subcategories")
    public SubcategoryResponse createSubcategory(@RequestBody SubcategoryRequest subcategoryRequest) {
        return subcategoryService.createSubcategory(subcategoryMapper.fromSubcategoryRequest(subcategoryRequest));
    }

    @GetMapping("/subcategories/{id}")
    public SubcategoryResponse getSubcategoryById(@PathVariable UUID id) {
        return subcategoryService.getSubcategoryById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    @PutMapping("/subcategories/{id}")
    public SubcategoryResponse updateSubcategory(@PathVariable UUID id, @RequestBody SubcategoryRequest updatedSubcategoryRequest) {
        return subcategoryService.updateSubcategory(id, subcategoryMapper.fromSubcategoryRequest(updatedSubcategoryRequest));
    }

    @DeleteMapping("/subcategories/{id}")
    public void deleteSubcategory(@PathVariable UUID id) {
        subcategoryService.deleteSubcategory(id);
    }
}
