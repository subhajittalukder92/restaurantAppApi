package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
