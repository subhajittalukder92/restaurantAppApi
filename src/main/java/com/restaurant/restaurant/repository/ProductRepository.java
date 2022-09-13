package com.restaurant.restaurant.repository;

import com.restaurant.restaurant.entity.Product;
import com.restaurant.restaurant.entity.ShoppingCart;
import com.restaurant.restaurant.payload.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT " +
            "new com.restaurant.restaurant.payload.response.ProductResponse(p.id,p.category,p.name,p.description,p.slug,p.menuType,p.price,p.discount,p.discountType,p.status,ci.quantity)" +
            "FROM Product p LEFT JOIN CartItems ci ON ci.shoppingCart = :shoppingCart AND ci.product.id=p.id",
            countQuery = "SELECT COUNT(*) FROM Product p"

    )
    Page<ProductResponse> getAllProductssByUser(@Param("shoppingCart") ShoppingCart shoppingCart, Pageable pageable);
}
