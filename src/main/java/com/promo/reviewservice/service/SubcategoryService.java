package com.promo.reviewservice.service;

import com.promo.reviewservice.dto.SubcategoryDTO;
import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.CategoryRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public List<SubcategoryDTO> getAllSubcategories() {
        return subcategoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SubcategoryDTO createSubcategory(SubcategoryDTO subcategoryDTO) {
        Subcategory subcategory = convertToEntity(subcategoryDTO);
        Category category = categoryRepository.findById(subcategoryDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        subcategory.setCategory(category);
        subcategory = subcategoryRepository.save(subcategory);
        return convertToDTO(subcategory);
    }

    public Optional<SubcategoryDTO> getSubcategoryById(Long id) {
        return subcategoryRepository.findById(id)
                .map(this::convertToDTO);
    }

    public SubcategoryDTO updateSubcategory(Long id, SubcategoryDTO updatedSubcategoryDTO) {
        return subcategoryRepository.findById(id)
                .map(subcategory -> {
                    subcategory.setName(updatedSubcategoryDTO.getName());
                    Category category = categoryRepository.findById(updatedSubcategoryDTO.getCategoryId())
                            .orElseThrow(() -> new RuntimeException("Category not found"));
                    subcategory.setCategory(category);
                    subcategory = subcategoryRepository.save(subcategory);
                    return convertToDTO(subcategory);
                })
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }

    public void deleteSubcategory(Long id) {
        subcategoryRepository.deleteById(id);
    }

    private SubcategoryDTO convertToDTO(Subcategory subcategory) {
        SubcategoryDTO subcategoryDTO = new SubcategoryDTO();
        subcategoryDTO.setId(subcategory.getId());
        subcategoryDTO.setName(subcategory.getName());
        subcategoryDTO.setCategoryId(subcategory.getCategory().getId());
        return subcategoryDTO;
    }

    private Subcategory convertToEntity(SubcategoryDTO subcategoryDTO) {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(subcategoryDTO.getId());
        subcategory.setName(subcategoryDTO.getName());
        return subcategory;
    }
}
