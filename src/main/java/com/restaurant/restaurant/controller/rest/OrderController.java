package com.restaurant.restaurant.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.restaurant.restaurant.payload.request.OrderRequest;
import com.restaurant.restaurant.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity placeOrder(@Valid  @RequestBody OrderRequest orderRequest) throws JsonProcessingException, IOException {
        return  orderService.placeOrder(orderRequest);
    }
}
