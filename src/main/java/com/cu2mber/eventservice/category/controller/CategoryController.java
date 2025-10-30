package com.cu2mber.eventservice.category.controller;

import com.cu2mber.eventservice.category.controller.specification.CategoryApiSepcification;
import com.cu2mber.eventservice.category.dto.CategoryResponse;
import com.cu2mber.eventservice.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * {@code CategoryController} 클래스는 행사 카테고리 관련 API 요청을 처리하는 REST 컨트롤러입니다.
 *
 * <p>
 *     본 컨트롤러는 {@link CategoryService}를 통해 카테고리 데이터를 조회하며,
 *     프론트엔드에서 행사 분류(예: 문화예술, 교육, 전시 등)를 표시할 때 사용되는
 *     카테고리 목록을 제공합니다.
 * </p>
 *
 * <ul>
 *   <li>전체 카테고리 목록 조회</li>
 * </ul>
 *
 * <p>
 *     기본 요청 경로는 {@code /api/events/categories}이며,
 *     모든 응답은 {@link ResponseEntity} 형태로 반환됩니다.
 * </p>
 */
@RestController
@RequestMapping("/api/events/categories")
@RequiredArgsConstructor
public class CategoryController implements CategoryApiSepcification {

    private final CategoryService categoryService;

    /**
     * 전체 행사 카테고리 목록을 조회합니다.
     *
     * @return {@link CategoryResponse} 객체 리스트를 포함한 {@link ResponseEntity}
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

}
