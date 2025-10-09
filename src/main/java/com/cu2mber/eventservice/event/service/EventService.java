package com.cu2mber.eventservice.event.service;

import com.cu2mber.eventservice.event.dto.EventDetailResponse;
import com.cu2mber.eventservice.event.dto.EventListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * {@code EventService}는 행사(Event) 관련 비즈니스 로직을 처리하는 서비스 인터페이스입니다.
 * <p>
 * 본 인터페이스는 행사의 목록 조회, 카테고리별 조회, 키워드 검색,
 * 단일 상세 조회 기능을 제공합니다. <br>
 * 실제 데이터 접근은 {@code EventRepository}를 통해 수행되며,
 * 이 서비스 계층에서는 DTO 변환 및 비즈니스 규칙을 담당합니다.
 * </p>
 */
public interface EventService {

    /**
     * 전체 행사 목록을 페이지 단위로 조회합니다.
     *
     * @param pageable 페이징 및 정렬 정보를 포함한 {@link Pageable} 객체
     * @return {@link EventListResponse} DTO 목록이 포함된 {@link Page}
     */
    Page<EventListResponse> getAllEvents(Pageable pageable);

    /**
     * 행사 제목에 특정 키워드가 포함된 행사 목록을 페이지 단위로 조회합니다.
     * <p>
     * 부분 일치 검색(contains) 방식으로 제목을 필터링합니다.
     * </p>
     *
     * @param keyword  검색할 키워드 (제목 내 포함될 문자열)
     * @param pageable 페이징 및 정렬 정보를 포함한 {@link Pageable} 객체
     * @return 검색 조건에 부합하는 {@link EventListResponse} DTO 목록이 포함된 {@link Page}
     */
    Page<EventListResponse> searchEventsByTitle(String keyword, Pageable pageable);

    /**
     * 특정 카테고리에 속한 행사 목록을 페이지 단위로 조회합니다.
     *
     * @param categoryNo 카테고리 번호
     * @param pageable   페이징 및 정렬 정보를 포함한 {@link Pageable} 객체
     * @return 해당 카테고리에 속한 {@link EventListResponse} DTO 목록이 포함된 {@link Page}
     */
    Page<EventListResponse> getEventsByCategory(Long categoryNo, Pageable pageable);

    /**
     * 행사 고유 번호(eventNo)를 기준으로 단일 행사 상세 정보를 조회합니다.
     *
     * @param eventNo 축제 고유 번호
     * @return 해당 번호에 해당하는 {@link EventDetailResponse} DTO, 존재하지 않으면 {@code null}
     */
    EventDetailResponse getEventDetail(Long eventNo);

}
