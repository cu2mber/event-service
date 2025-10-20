package com.cu2mber.eventservice.category.dto;


import com.cu2mber.eventservice.category.domain.Category;

public record CategoryResponse(
    Long categoryNo,
    String categoryName
){
    public static CategoryResponse from(Category category){
        return new CategoryResponse(
                category.getCategoryNo(),
                category.getCategoryName()
        );

    }
}