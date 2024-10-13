package com.promo.reviewservice.repository;

import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAll(Pageable pageable);

    // Фильтрация по диапазону дат
    Page<Review> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Фильтрация по подкатегории
    Page<Review> findBySubcategory(Subcategory subcategory, Pageable pageable);

    // Фильтрация по категории (через подкатегорию)
    @Query("SELECT r FROM Review r JOIN r.subcategory s JOIN s.category c WHERE c.id = :categoryId")
    Page<Review> findByCategory(@Param("categoryId") Long categoryId, Pageable pageable);

    // Сортировка по оценке
    Page<Review> findAllByOrderByRatingAsc(Pageable pageable);
    Page<Review> findAllByOrderByRatingDesc(Pageable pageable);

    // Сортировка по дате
    Page<Review> findAllByOrderByCreatedAtAsc(Pageable pageable);
    Page<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Комбинированный запрос: фильтрация по диапазону дат и сортировка по дате
    Page<Review> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Комбинированный запрос: фильтрация по подкатегории и сортировка по оценке
    Page<Review> findBySubcategoryOrderByRatingDesc(Subcategory subcategory, Pageable pageable);

    // Комбинированный запрос: фильтрация по категории и сортировка по дате
    @Query("SELECT r FROM Review r JOIN r.subcategory s JOIN s.category c WHERE c.id = :categoryId ORDER BY r.createdAt DESC")
    Page<Review> findByCategoryOrderByCreatedAtDesc(@Param("categoryId") Long categoryId, Pageable pageable);
}
