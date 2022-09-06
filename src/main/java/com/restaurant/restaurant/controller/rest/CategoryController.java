package com.restaurant.restaurant.controller.rest;

import com.restaurant.restaurant.entity.Category;
import com.restaurant.restaurant.payload.PagedResponse;
import com.restaurant.restaurant.payload.response.CategoryResponse;
import com.restaurant.restaurant.service.CategoryService;
import com.restaurant.restaurant.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/categories")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public PagedResponse<CategoryResponse> getAll(@RequestParam(name = "page",required = false,defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                  @RequestParam(name = "size",required = false,defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size){
      return  categoryService.findAll(page,size);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
        return categoryService.findById(id);
    }

}
