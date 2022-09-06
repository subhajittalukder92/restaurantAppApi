package com.restaurant.restaurant.utils;

import com.restaurant.restaurant.entity.Product;
import com.restaurant.restaurant.entity.User;
import com.restaurant.restaurant.jwtsecurity.CustomUserDetails;
import com.restaurant.restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Helper {
    @Autowired
    private UserRepository userRepository;


    public User getUser(){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
     //   System.out.println(userRepository.findById(userDetails.getId()));
        return userRepository.findById(userDetails.getId()).get();
    }
    public Date getCurrentDate(){

        return new Date();
    }

    public Double calculateProductPrice(Product product, Integer quantity) {
        Double totalPrice = 0.00 ;
        if(product.getDiscountType().toLowerCase().equals("percentage")){
            totalPrice = (product.getPrice() *(100 - product.getDiscount())/100) * quantity ;

        }else if(product.getDiscountType().toLowerCase().equals("amount") ) {
            totalPrice = (product.getPrice() -product.getDiscount()) * quantity ;
        }else{
            totalPrice = product.getPrice()  * quantity ;
        }
        return totalPrice;
    }
}
