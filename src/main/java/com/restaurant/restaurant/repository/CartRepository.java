package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.ShoppingCart;
import com.restaurant.restaurant.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<ShoppingCart, Long> {
    public ShoppingCart findShoppingCartByUser(User userId);
}
