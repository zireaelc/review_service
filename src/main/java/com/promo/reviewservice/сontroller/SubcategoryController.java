package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.dto.CategoryDTO;
import com.promo.reviewservice.dto.SubcategoryDTO;
import com.promo.reviewservice.model.Category;
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

    private SubcategoryDTO toDto(Subcategory entity) {
        return modelMapper.map(entity, SubcategoryDTO.class);
    }

    private Subcategory toEntity(SubcategoryDTO dto) {
        return modelMapper.map(dto, Subcategory.class);
    }

    @GetMapping
    public List<SubcategoryDTO> getAllSubcategories() {
        return subcategoryService.getAllSubcategories().stream()
                .map(this::toDto)
                .toList();
    }

    @PostMapping
    public SubcategoryDTO createSubcategory(@RequestBody SubcategoryDTO subcategoryDTO) {
        return toDto(subcategoryService.createSubcategory(toEntity(subcategoryDTO)));
    }

    @GetMapping("/{id}")
    public SubcategoryDTO getSubcategoryById(@PathVariable UUID id) {
        return subcategoryService.getSubcategoryById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    @PutMapping("/{id}")
    public SubcategoryDTO updateSubcategory(@PathVariable UUID id, @RequestBody SubcategoryDTO updatedSubcategoryDTO) {
        return toDto(subcategoryService.updateSubcategory(id, toEntity(updatedSubcategoryDTO)));
    }

    @DeleteMapping("/{id}")
    public void deleteSubcategory(@PathVariable UUID id) {
        subcategoryService.deleteSubcategory(id);
    }
}
