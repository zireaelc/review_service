package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.subcategory.SubcategoryRequest;
import com.promo.reviewservice.dto.subcategory.SubcategoryResponse;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private final ModelMapper modelMapper;

    private SubcategoryRequest toDto(Subcategory entity) {
        return modelMapper.map(entity, SubcategoryRequest.class);
    }

    private Subcategory toEntity(SubcategoryRequest dto) {
        return modelMapper.map(dto, Subcategory.class);
    }

    @GetMapping("/subcategories")
    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryService.getAllSubcategories();
    }

    @PostMapping("/subcategories")
    public SubcategoryResponse createSubcategory(@RequestBody SubcategoryRequest subcategoryRequest) {
        return subcategoryService.createSubcategory(toEntity(subcategoryRequest));
    }

    @GetMapping("/subcategories/{id}")
    public SubcategoryResponse getSubcategoryById(@PathVariable UUID id) {
        return subcategoryService.getSubcategoryById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    @PutMapping("/subcategories/{id}")
    public SubcategoryResponse updateSubcategory(@PathVariable UUID id, @RequestBody SubcategoryRequest updatedSubcategoryRequest) {
        return subcategoryService.updateSubcategory(id, toEntity(updatedSubcategoryRequest));
    }

    @DeleteMapping("/subcategories/{id}")
    public void deleteSubcategory(@PathVariable UUID id) {
        subcategoryService.deleteSubcategory(id);
    }
}
