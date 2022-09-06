package com.restaurant.restaurant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurant.restaurant.payload.request.OrderRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface OrderService {
    public ResponseEntity placeOrder(OrderRequest orderRequest) throws JsonProcessingException;
}
