package com.cu2mber.eventservice.category.controller.specification;

import com.cu2mber.eventservice.category.dto.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Category", description = "행사 카테고리 관련 API")
public interface CategoryApiSepcification {

    @Operation(summary = "행사 카테고리 전체 조회", description = "모든 행사를 페이지 단위로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema=true),
            @ApiResponse(responseCode = "404", description = "데이터 없음")

    })
    @GetMapping
    ResponseEntity<List<CategoryResponse>> getAllCategories();
}
