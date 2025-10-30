package com.cu2mber.eventservice.category.service.impl;

import com.cu2mber.eventservice.category.dto.CategoryResponse;
import com.cu2mber.eventservice.category.repository.CategoryRepository;
import com.cu2mber.eventservice.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .toList();
    }
}
