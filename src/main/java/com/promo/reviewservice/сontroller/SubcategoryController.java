package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.SubcategoryDTO;
import com.promo.reviewservice.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryService subcategoryService;

    @GetMapping
    public List<SubcategoryDTO> getAllSubcategory() {
        return subcategoryService.getAllSubcategories();
    }

    @PostMapping
    public SubcategoryDTO createSubcategory(@RequestBody SubcategoryDTO subcategoryDTO) {
        return subcategoryService.createSubcategory(subcategoryDTO);
    }

    @GetMapping("/{id}")
    public SubcategoryDTO getSubcategoryById(@PathVariable Long id) {
        return subcategoryService.getSubcategoryById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    @PutMapping("/{id}")
    public SubcategoryDTO updateSubcategory(@PathVariable Long id, @RequestBody SubcategoryDTO updatedSubcategoryDTO) {
        return subcategoryService.updateSubcategory(id, updatedSubcategoryDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteSubcategory(@PathVariable Long id) {
        subcategoryService.deleteSubcategory(id);
    }
}
