package com.cu2mber.eventservice.event.service.impl;

import com.cu2mber.eventservice.category.domain.Category;
import com.cu2mber.eventservice.category.repository.CategoryRepository;
import com.cu2mber.eventservice.event.dto.EventDetailResponse;
import com.cu2mber.eventservice.event.dto.EventListResponse;
import com.cu2mber.eventservice.event.repository.EventRepository;
import com.cu2mber.eventservice.event.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link EventService} 구현체입니다.
 * <p>
 * 모든 메서드는 읽기 전용 트랜잭션(@Transactional(readOnly = true))으로 수행되며,
 * 행사 데이터를 최신 등록순으로 정렬하여 조회합니다.
 * </p>
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

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
        log.info("[getAllEvents] 전체 행사 목록 조회 요청 - page: {}", pageable.getPageNumber());

        // 최신 등록순(최근 크롤링된 순) 정렬
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");

        // 한 페이지당 5개씩 고정 및 최신순 정렬
        Pageable fixedPageable = PageRequest.of(pageable.getPageNumber(), 5, sort);

        Page<EventListResponse> events = eventRepository.findAll(fixedPageable)
                .map(EventListResponse::from);

        log.info("[getAllEvents] 조회된 행사 개수: {}", events.getTotalElements());
        return events;

    }

    @Override
    public Page<EventListResponse> searchEventsByTitle(String keyword, Pageable pageable) {
        log.info("[searchEventsByTitle] 키워드 검색 요청 - keyword: {}", keyword);

        Page<EventListResponse> results = eventRepository.findByEventTitleContaining(keyword, pageable)
                .map(EventListResponse::from);

        log.info("[searchEventsByTitle] 검색 결과 개수 : {}", results.getTotalElements());
        return results;
    }

    @Override
    public Page<EventListResponse> getEventsByCategory(String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(categoryName);

        log.info("[getEventsByCategory] 특정 카테고리 검색 : {}", category);

        Page<EventListResponse> results = eventRepository.findByCategory_CategoryNo(category.getCategoryNo(), pageable)
                .map(EventListResponse::from);

        log.info("[getEventsByCategory] 조회된 행사 개수: {}", results.getTotalElements());
        return results;
    }

    @Override
    public EventDetailResponse getEventDetail(Long eventNo) {
        log.info("[getEventDetail] 행사 상세 조회 요청 - eventNo: {}", eventNo);

        EventDetailResponse response = eventRepository.findByEventNo(eventNo)
                .map(EventDetailResponse::from)
                .orElseThrow(() -> {
                    log.warn("[getEventDetail] 행사 조회 실패 - 존재하지 않는 eventNo: {}", eventNo);
                    return new EntityNotFoundException("해당 번호의 행사를 찾을 수 없습니다.");
                });

        log.info("[getEventDetail] 행사 상세 조회 성공 - eventNo: {}", eventNo);
        return response;
    }
}
