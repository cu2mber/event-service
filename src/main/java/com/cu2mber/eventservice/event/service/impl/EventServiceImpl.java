package com.cu2mber.eventservice.event.service.impl;

import com.cu2mber.eventservice.event.dto.EventListResponse;
import com.cu2mber.eventservice.event.repository.EventRepository;
import com.cu2mber.eventservice.event.service.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    /**
     * 전체 행사 목록을 페이지 단위로 조회합니다.
     * <p>
     * 한 페이지당 5개의 행사를 반환합니다.
     * </p>
     *
     * @param pageable 요청된 페이징 정보
     * @return {@link EventListResponse} DTO가 포함된 {@link Page}
     */
    @Override
    public Page<EventListResponse> getAllEvents(Pageable pageable) {
        // 최신 등록순(최근 크롤링된 순) 정렬
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 한 페이지당 5개씩 고정 및 최신순 정렬
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 5, sort);

        return eventRepository.findAll(fixedPageable)
                .map(EventListResponse::from);

    }

    @Override
    public Page<EventListResponse> searchEventsByTitle(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<EventListResponse> getEventsByCategory(Long categoryNo, Pageable pageable) {
        return null;
    }

    @Override
    public EventListResponse getEventDetail(Long eventNo) {
        return null;
    }
}
