package com.restaurant.restaurant.service;
import com.restaurant.restaurant.entity.Product;
import com.restaurant.restaurant.payload.PagedResponse;
import com.restaurant.restaurant.payload.response.ProductResponse;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface ProductService {
    public PagedResponse<ProductResponse> getAllProducts(Integer page, Integer size);
    public ResponseEntity<Product> findById(Long id);
    public List<ProductResponse> mapProductToResponse(List<Product> products);
    public PagedResponse<ProductResponse> fetchAllProducts(Integer page, Integer size);

}
