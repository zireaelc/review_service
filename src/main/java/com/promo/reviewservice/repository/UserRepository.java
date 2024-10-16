package com.promo.reviewservice.repository;

import com.promo.reviewservice.model.Role;
import com.promo.reviewservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findByRole(Role role);
}
