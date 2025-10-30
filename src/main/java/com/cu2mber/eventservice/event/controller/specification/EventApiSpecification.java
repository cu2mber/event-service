package com.cu2mber.eventservice.event.controller.specification;

import com.cu2mber.eventservice.event.dto.EventDetailResponse;
import com.cu2mber.eventservice.event.dto.EventListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Event", description = "행사 관련 API")
public interface EventApiSpecification {

    @Operation(summary = "행사 전체 조회", description = "모든 행사를 페이지 단위로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema=true),
            @ApiResponse(responseCode = "404", description = "데이터 없음")

    })
    @GetMapping
    ResponseEntity<Page<EventListResponse>> getAllEvents(Pageable pageable);

    @Operation(summary = "특정 행사 이름 검색", description = "검색한 행사를 페이지 단위로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema=true),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터"),
            @ApiResponse(responseCode = "404", description = "데이터 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/search")
    ResponseEntity<Page<EventListResponse>> getSearchEventsByTitle(@RequestParam String keyword, Pageable pageable);

    @Operation(summary = "특정 행사 이름 검색", description = "검색한 행사를 페이지 단위로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema=true),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터"),
            @ApiResponse(responseCode = "404", description = "데이터 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/categories/{category-no}")
    ResponseEntity<Page<EventListResponse>> getEventsByCategory(@PathVariable("category-no") Long categoryNo, Pageable pageable);

    @Operation(summary = "행사 상세 조회", description = "행사 번호를 기준으로 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", useReturnTypeSchema=true),
            @ApiResponse(responseCode = "404", description = "해당 행사를 찾을 수 없음"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/{event-no}")
    ResponseEntity<EventDetailResponse> getEventDetail(@PathVariable("event-no") Long eventNo);

}
