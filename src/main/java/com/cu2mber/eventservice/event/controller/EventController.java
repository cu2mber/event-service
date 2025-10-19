package com.cu2mber.eventservice.event.controller;

import com.cu2mber.eventservice.event.dto.EventDetailResponse;
import com.cu2mber.eventservice.event.dto.EventListResponse;
import com.cu2mber.eventservice.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    /**
     * 페이지당 5개씩 전체 행사를 조회합니다.
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
