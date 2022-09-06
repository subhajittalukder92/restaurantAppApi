package com.restaurant.restaurant.service.impl;

import com.restaurant.restaurant.entity.Category;
import com.restaurant.restaurant.exception.ResourceNotFoundException;
import com.restaurant.restaurant.payload.PagedResponse;
import com.restaurant.restaurant.payload.response.CategoryResponse;
import com.restaurant.restaurant.repository.CategoryRepository;
import com.restaurant.restaurant.service.CategoryService;
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

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PagedResponse<CategoryResponse> findAll(Integer page, Integer size) {
               Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryResponse> content = categories.getNumberOfElements() == 0 ? Collections.emptyList()
                : mapCategoryToResponse(categories.getContent()) ;
        return new PagedResponse<>(content, categories.getNumber(),categories.getSize(),categories.getTotalPages(),
                categories.getTotalElements(),categories.isLast());
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Category","id",id));
        CategoryResponse categoryResponse= modelMapper.map(category, CategoryResponse.class);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }


    @Override
    public List<CategoryResponse> mapCategoryToResponse(List<Category> categories) {
        List<CategoryResponse> allResponses = new ArrayList<>();

        for (int i=0; i< categories.size();i++){
            Category category = categories.get(i);
            allResponses.add(modelMapper.map(category, CategoryResponse.class));
        }
        return allResponses;
    }
}
