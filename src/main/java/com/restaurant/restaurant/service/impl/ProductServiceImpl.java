package com.restaurant.restaurant.service.impl;

import com.restaurant.restaurant.entity.Product;
import com.restaurant.restaurant.entity.ShoppingCart;
import com.restaurant.restaurant.exception.ResourceNotFoundException;
import com.restaurant.restaurant.payload.PagedResponse;
import com.restaurant.restaurant.payload.response.ProductResponse;
import com.restaurant.restaurant.repository.CartRepository;
import com.restaurant.restaurant.repository.ProductRepository;
import com.restaurant.restaurant.service.ProductService;
import com.restaurant.restaurant.utils.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private Helper helper;
    @Override
    public PagedResponse<ProductResponse> getAllProducts(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        Page<Product> products =productRepository.findAll(pageable);
        List<ProductResponse> content = products.getTotalElements() == 0 ? Collections.emptyList()
                : mapProductToResponse(products.getContent());

        return new PagedResponse<>(content,products.getNumber(),products.getSize(),products.getTotalPages(),
                products.getTotalElements(),products.isLast());
    }

    @Override
    public ResponseEntity<Product> findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Product","id", id));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public List<ProductResponse> mapProductToResponse(List<Product> products) {
        List<ProductResponse> allResponses = new ArrayList<>();
        for (int i=0; i<products.size();i++){
            allResponses.add(modelMapper.map(products.get(i), ProductResponse.class));
        }
        return allResponses;
    }
    @Override
    public PagedResponse<ProductResponse> fetchAllProducts(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"id");
        ShoppingCart cart = cartRepository.findShoppingCartByUser(helper.getUser());
        Page<ProductResponse> products =productRepository.getAllProductssByUser(cart, pageable);
        System.out.println("Page: "+products.getSize());
        List<ProductResponse> content = products.getTotalElements() == 0 ? Collections.emptyList()
                : products.getContent();

        return new PagedResponse<>(content,products.getNumber(),products.getSize(),products.getTotalPages(),
                products.getTotalElements(),products.isLast());

    }
}
