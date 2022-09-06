package com.restaurant.restaurant.service;

import com.restaurant.restaurant.entity.Product;
import com.restaurant.restaurant.entity.User;
import org.springframework.http.ResponseEntity;

public interface CartService {
    public ResponseEntity<?> findShoppingCartByUserId(User userId);
    public ResponseEntity<?> addItem(Long productId);

    ResponseEntity<?> removeItem(Long productId);
}
