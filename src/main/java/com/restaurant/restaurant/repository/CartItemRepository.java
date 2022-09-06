package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.CartItems;
import com.restaurant.restaurant.entity.Product;
import com.restaurant.restaurant.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItems, Long> {
   // public Object findByProductAndShoppingCart(Product product, ShoppingCart cart);
}
