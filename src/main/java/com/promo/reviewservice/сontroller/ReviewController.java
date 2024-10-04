package com.promo.reviewservice.сontroller;

import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewRepository repository;

    @GetMapping
    public List<Review> getAllCategory() {
        return repository.findAll();
    }

    @PostMapping
    public Review createReview(@RequestBody Review review){
        return repository.save(review);
    }
}
