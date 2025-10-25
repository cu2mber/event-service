package com.cu2mber.eventservice.category.service;

import com.cu2mber.eventservice.category.dto.CategoryResponse;
import com.cu2mber.eventservice.category.repository.CategoryRepository;

import java.util.List;

/**
 * {@code CategoryService} 인터페이스는 행사 카테고리 관련 비즈니스 로직을 정의합니다.
 *
 * <p>
 *    이 서비스는 {@link CategoryRepository}를 통해 카테고리 데이터를 조회하는 기능을 제공합니다.
 * </p>
 */
public interface CategoryService {
    List<CategoryResponse> getAllCategories();
}
