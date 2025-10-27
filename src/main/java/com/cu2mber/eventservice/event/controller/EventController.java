package com.cu2mber.eventservice.event.controller;

import com.cu2mber.eventservice.event.controller.specification.EventApiSpecification;
import com.cu2mber.eventservice.event.dto.EventDetailResponse;
import com.cu2mber.eventservice.event.dto.EventListResponse;
import com.cu2mber.eventservice.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * {@code EventController} 클래스는 행사 관련 REST API 요청을 처리하는 컨트롤러입니다.
 *
 * <p>
 *     이 컨트롤러는 {@link EventService}를 통해 행사 데이터의 조회 로직을 위임받아 수행하며,
 *     다음과 같은 주요 기능을 제공합니다.
 * </p>
 *
 * <ul>
 *   <li>전체 행사 목록 조회 — 페이지네이션 적용</li>
 *   <li>키워드 기반 행사 검색</li>
 *   <li>카테고리별 행사 목록 조회</li>
 *   <li>특정 행사 상세 정보 조회</li>
 * </ul>
 *
 * <p>
 *     각 API는 {@link ResponseEntity}를 통해 JSON 형태의 응답 본문을 반환하며,
 *     목록 조회 시에는 {@link org.springframework.data.domain.Page} 객체를 포함하여
 *     페이지 정보와 함께 결과를 제공합니다.
 * </p>
 *
 * <p>
 *     이 컨트롤러의 기본 경로는 {@code /api/events}이며,
 *     모든 요청은 RESTful 규약에 따라 GET 메서드로 조회됩니다.
 * </p>
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController implements EventApiSpecification {

    private final EventService eventService;

    /**
     * 전체 행사를 페이지 단위로 조회합니다.
     *
     * @param pageable 페이지 번호 및 정렬 기준을 담은 {@link Pageable} 객체
     * @return {@link ResponseEntity} 형태의 응답 본문으로,
     *         {@link EventListResponse} 목록이 포함된 {@link Page} 객체를 반환합니다.
     */
    @GetMapping
    public ResponseEntity<Page<EventListResponse>> getAllEvents(Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    /**
     * 행사 제목에 특정 키워드가 포함된 행사 목록을 페이지 단위로 조회합니다.
     *
     * @param keyword
     * @param pageable
     * @return {@link ResponseEntity} 형태의 응답 본문으로,
     *         {@link EventListResponse} 목록이 포함된 {@link Page} 객체를 반환합니다.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<EventListResponse>> getSearchEventsByTitle(@RequestParam String keyword, Pageable pageable){
        return ResponseEntity.ok(eventService.searchEventsByTitle(keyword, pageable));
    }

    /**
     * 특정 카테고리에 속한 행사 목록을 페이지 단위로 조회합니다.
     *
     * @param categoryNo
     * @param pageable
     * @return {@link ResponseEntity} 형태의 응답 본문으로,
     *         {@link EventListResponse} 목록이 포함된 {@link Page} 객체를 반환합니다.
     */
    @GetMapping("/categories/{category-no}")
    public ResponseEntity<Page<EventListResponse>> getEventsByCategory(@PathVariable("category-no") Long categoryNo, Pageable pageable){
        return ResponseEntity.ok(eventService.getEventsByCategory(categoryNo, pageable));
    }

    /**
     * 특정 행사의 상세 정보를 조회합니다.
     *
     * @param eventNo 조회할 행사의 고유 식별자
     * @return 요청한 행사 번호에 해당하는 {@link EventDetailResponse} 객체를 포함한 {@link ResponseEntity}
     */
    @GetMapping(value = "/{event-no}")
    public ResponseEntity<EventDetailResponse> getEventDetail(@PathVariable("event-no") Long eventNo){
        EventDetailResponse response = eventService.getEventDetail(eventNo);

        return ResponseEntity.ok(response);
    }

}
