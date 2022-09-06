package com.restaurant.restaurant.controller.rest;

import com.restaurant.restaurant.payload.request.CartRequest;
import com.restaurant.restaurant.repository.CartRepository;
import com.restaurant.restaurant.repository.UserRepository;
import com.restaurant.restaurant.service.CartService;
import com.restaurant.restaurant.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Helper helper;

    @GetMapping("")
    public ResponseEntity<?> getCartItems(){
    //    return  new ResponseEntity<>(cartService.findShoppingCartByUserId(Helper.getUserId()), HttpStatus.OK);

        return cartService.findShoppingCartByUserId(helper.getUser()) ;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequest addToCartRequet){
       // System.out.println(userRepository.findById(Long.valueOf(1)).get());
       return cartService.addItem(addToCartRequet.getProductId());


    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeFromCart(@Valid @RequestBody CartRequest addToCartRequet){

        return cartService.removeItem(addToCartRequet.getProductId());


    }
}
