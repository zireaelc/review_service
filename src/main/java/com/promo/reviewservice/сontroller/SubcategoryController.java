package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.model.Subcategory;
import com.promo.reviewservice.repository.SubcategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subcategories")
@RequiredArgsConstructor
public class SubcategoryController {
    private final SubcategoryRepository repository;

    @GetMapping
    public List<Subcategory> getAllSubcategory() {
        return repository.findAll();
    }

    @PostMapping
    public Subcategory createSubcategory(@RequestBody Subcategory subcategory){
        return repository.save(subcategory);
    }
}
