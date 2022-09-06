package com.restaurant.restaurant.payload.response;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String status;
}
