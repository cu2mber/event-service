package com.cu2mber.eventservice.category.service;

import com.cu2mber.eventservice.category.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
}
