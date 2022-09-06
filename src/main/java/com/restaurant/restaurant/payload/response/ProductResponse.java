package com.restaurant.restaurant.payload.response;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private CategoryResponse category;
    private String name;
    private String description;
    private String slug;
    private String menuType;
    private Double price;
    private Double discount;
    private String discountType;
    private String status;

}
