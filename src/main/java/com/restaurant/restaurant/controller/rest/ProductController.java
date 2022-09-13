package com.restaurant.restaurant.controller.rest;

import com.restaurant.restaurant.entity.Product;
import com.restaurant.restaurant.payload.PagedResponse;
import com.restaurant.restaurant.payload.response.ProductResponse;
import com.restaurant.restaurant.service.ProductService;
import com.restaurant.restaurant.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/products")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public PagedResponse<ProductResponse> showAllProducts(
            @RequestParam(name = "page",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size
            ){
       // return productService.getAllProducts(page,size);
        return productService.fetchAllProducts(page,size);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> showProductById(@PathVariable("id") Long id){
        return productService.findById(id);
    }
}
