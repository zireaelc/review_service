package com.promo.reviewservice.repository;

import com.promo.reviewservice.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubcategoryRepository extends JpaRepository<Subcategory, UUID> {
}
