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
     * 페이지당 5개씩 전체 행사를 불러오는 메소드입니다.
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<EventListResponse>> getAllEvents(Pageable pageable) {
        return ResponseEntity.ok(eventService.getAllEvents(pageable));
    }

    @GetMapping(value = "/{event-no}")
    public ResponseEntity<EventDetailResponse> getEventDetail(@PathVariable("event-no") Long eventNo){
        EventDetailResponse response = eventService.getEventDetail(eventNo);

        return ResponseEntity.ok().body(response);
    }



}
