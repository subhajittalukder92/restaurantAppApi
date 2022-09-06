package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
