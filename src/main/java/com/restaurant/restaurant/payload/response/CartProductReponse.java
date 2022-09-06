package com.restaurant.restaurant.payload.response;

import com.restaurant.restaurant.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartProductReponse {
    private Product product;
    private Integer quantity;
    private Double price;
}
