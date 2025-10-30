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

import java.util.Optional;

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
     *     프론트엔드에서 전달된 페이지 번호(page)와 페이지 크기(size)를 기반으로
     *     최신순으로 정렬된 행사 목록을 반환합니다.
     *     정렬 기준은 해당 서비스에서 event_no 내림차순으로 고정됩니다.
     * </p>
     *
     * @param pageable 요청된 페이징 정보
     * @return {@link EventListResponse} DTO가 포함된 {@link Page}
     */
    @Override
    public Page<EventListResponse> getAllEvents(Pageable pageable) {

        log.info("[getAllEvents] 전체 행사 목록 조회 요청 - page: {}", pageable.getPageNumber());

        Pageable fixedPageable = createPageable(pageable);

        Page<EventListResponse> events = eventRepository.findAll(fixedPageable)
                .map(EventListResponse::from);

        log.info("[getAllEvents] 조회된 행사 개수: {}", events.getTotalElements());
        return events;

    }

    @Override
    public Page<EventListResponse> searchEventsByTitle(String keyword, Pageable pageable) {

        log.info("[searchEventsByTitle] 키워드 검색 요청 - keyword: {}", keyword);

        Pageable fixedPageable = createPageable(pageable);

        Page<EventListResponse> results = eventRepository.findByEventTitleContaining(keyword, fixedPageable)
                .map(EventListResponse::from);

        log.info("[searchEventsByTitle] 검색 결과 개수 : {}", results.getTotalElements());
        return results;
    }

    @Override
    public Page<EventListResponse> getEventsByCategory(Long categoryNo, Pageable pageable) {

        Optional<Category> category = categoryRepository.findById(categoryNo);

        if(category.isPresent()){
            log.info("[getEventsByCategory] 특정 카테고리 검색 : {}", category);
        } else {
            log.error("[getEventsByCategory] 특정 카테고리 검색 실패");
        }

        Pageable fixedPageable = createPageable(pageable);

        Page<EventListResponse> results = eventRepository.findByCategory_CategoryNo(category.get().getCategoryNo(), fixedPageable)
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

    /**
     * 프론트에서 전달된 page와 size를 기반으로
     * 최신 등록순(createdAt DESC) 정렬된 Pageable 객체를 생성합니다.
     */
    private Pageable createPageable(Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt"); // 최신순 고정

        return PageRequest.of(page, size, sort);
    }
}
