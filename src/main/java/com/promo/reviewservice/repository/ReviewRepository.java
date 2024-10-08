package com.promo.reviewservice.repository;

import com.promo.reviewservice.model.Review;
import com.promo.reviewservice.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Фильтрация по диапазону дат
    List<Review> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Фильтрация по подкатегории
    List<Review> findBySubcategory(Subcategory subcategory);

    // Фильтрация по категории (через подкатегорию)
    @Query("SELECT r FROM Review r JOIN r.subcategory s JOIN s.category c WHERE c.id = :categoryId")
    List<Review> findByCategory(@Param("categoryId") Long categoryId);

    // Сортировка по оценке
    List<Review> findAllByOrderByRatingAsc();
    List<Review> findAllByOrderByRatingDesc();

    // Сортировка по дате
    List<Review> findAllByOrderByCreatedAtAsc();
    List<Review> findAllByOrderByCreatedAtDesc();

    // Комбинированный запрос: фильтрация по диапазону дат и сортировка по дате
    List<Review> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

    // Комбинированный запрос: фильтрация по подкатегории и сортировка по оценке
    List<Review> findBySubcategoryOrderByRatingDesc(Subcategory subcategory);

    // Комбинированный запрос: фильтрация по категории и сортировка по дате
    @Query("SELECT r FROM Review r JOIN r.subcategory s JOIN s.category c WHERE c.id = :categoryId ORDER BY r.createdAt DESC")
    List<Review> findByCategoryOrderByCreatedAtDesc(@Param("categoryId") Long categoryId);
}
