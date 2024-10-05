package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.CategoryRepository;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryRepository repository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public List<Subcategory> getAllSubcategory() {
        return repository.findAll();
    }

    @PostMapping
    public Subcategory createSubcategory(@RequestBody Subcategory subcategory){
        Category category = categoryRepository.findById(subcategory.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        subcategory.setCategory(category);
        return repository.save(subcategory);
    }
}
