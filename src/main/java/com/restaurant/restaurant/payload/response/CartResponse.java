package com.restaurant.restaurant.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartResponse {


    private List<CartProductReponse> cartProducts;
    private double cartTotal;

}
