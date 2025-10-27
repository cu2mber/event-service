package com.cu2mber.eventservice.category.dto;


import com.cu2mber.eventservice.category.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * {@code CategoryResponse} 레코드는 단일 행사 카테고리 정보를 클라이언트에 전달하기 위한 DTO(Data Transfer Object)입니다.
 *
 * <p>
 *     이 클래스는 {@link Category} 엔티티로부터 필요한 정보를 추출하여 불변 객체로 제공하며,
 *     REST API 응답 시 사용됩니다.
 * </p>
 *
 * <p>
 *     모든 필드는 {@code final}이며, 생성 이후 수정이 불가능합니다.
 * </p>
 *
 * <ul>
 *   <li>{@code categoryNo} — 카테고리 고유 번호</li>
 *   <li>{@code categoryName} — 카테고리 이름</li>
 * </ul>
 *
 * <p>
 *     {@link #from(Category)} 정적 팩토리 메서드를 통해 {@link Category} 엔티티로부터 DTO로 변환할 수 있습니다.
 * </p>
 */
public record CategoryResponse(
    @Schema(name = "카테고리 번호")
    Long categoryNo,
    @Schema(name = "카테고리 이름")
    String categoryName
){
    public static CategoryResponse from(Category category){
        return new CategoryResponse(
                category.getCategoryNo(),
                category.getCategoryName()
        );

    }
}