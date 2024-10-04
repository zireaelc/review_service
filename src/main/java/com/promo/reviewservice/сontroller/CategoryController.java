package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.model.Category;
import com.promo.reviewservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository repository;

    @GetMapping
    public List<Category> getAllCategory() {
        return repository.findAll();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category){
        return repository.save(category);
    }
}
