package com.restaurant.restaurant.payload;

import lombok.Data;

import java.util.List;

@Data
public class PagedResponse<T> {
    private List<T> contents;
    private int page;
    private int size;
    private int totalPages;
    private Long totalElements;
    private Boolean last;
    public PagedResponse(List<T> contents, int page, int size, int totalPages, Long totalElements, Boolean last) {
        this.contents = contents;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.last = last;
    }
    public Boolean isLast(){
        return last;
    }

}
