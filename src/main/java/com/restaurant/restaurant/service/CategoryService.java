package com.restaurant.restaurant.service;

import com.restaurant.restaurant.entity.Category;
import com.restaurant.restaurant.payload.PagedResponse;
import com.restaurant.restaurant.payload.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    public PagedResponse<CategoryResponse> findAll(Integer page, Integer size);
    public ResponseEntity<?> findById(Long id);
    public List<CategoryResponse> mapCategoryToResponse(List<Category> categories);
}
