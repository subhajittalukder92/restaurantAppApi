package com.restaurant.restaurant.payload.response;

import com.restaurant.restaurant.entity.Category;
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
    private Integer quantity;

    public ProductResponse(Long id, Category category, String name, String description, String slug,
                           String menuType, Double price, Double discount, String discountType, String status, Integer quantity)
    {
        this.id = id;
        this.category = mapToCategoryResponse(category);
        this.name = name;
        this.description = description;
        this.slug = slug;
        this.menuType = menuType;
        this.price = price;
        this.discount = discount;
        this.discountType = discountType;
        this.status = status;
        this.quantity = quantity == null ? 0 : quantity;
    }

    public CategoryResponse mapToCategoryResponse(Category category){
        return new CategoryResponse(category.getId(), category.getName(), category.getSlug(), category.getDescription(), category.getStatus());
    }
}
