package com.restaurant.restaurant.payload.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.restaurant.entity.AbstractClass;
import com.restaurant.restaurant.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date orderDate;
    private Address address;
    private String orderNo;
    private Double orderAmount;
    private Double DiscountAmt;
    private Double totalAmount;
    private String paymentMethod;
    private List<OrderDetailResponse> orderDetails = new ArrayList<>();



}
