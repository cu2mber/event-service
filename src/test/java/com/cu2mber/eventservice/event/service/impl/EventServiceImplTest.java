package com.cu2mber.eventservice.event.service.impl;

import com.cu2mber.eventservice.category.domain.Category;
import com.cu2mber.eventservice.category.repository.CategoryRepository;
import com.cu2mber.eventservice.event.domain.Event;
import com.cu2mber.eventservice.event.dto.EventDetailResponse;
import com.cu2mber.eventservice.event.dto.EventListResponse;
import com.cu2mber.eventservice.event.repository.EventRepository;
import com.cu2mber.eventservice.localgov.domain.LocalGov;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * {@code EventServiceImplTest} 클래스는 {@link com.cu2mber.eventservice.event.service.impl.EventServiceImpl}
 * 클래스의 서비스 로직을 단위 테스트하기 위한 클래스입니다.
 *
 * <p>
 *     이 테스트는 JUnit 5와 Mockito를 기반으로 작성되었으며,
 *     {@link org.mockito.junit.jupiter.MockitoExtension}을 통해 Mockito 환경을 확장합니다.
 *     데이터베이스 접근 없이 {@link com.cu2mber.eventservice.event.repository.EventRepository}와
 *     {@link com.cu2mber.eventservice.category.repository.CategoryRepository}를 Mock 객체로 대체하여
 *     서비스 계층의 동작을 독립적으로 검증합니다.
 * </p>
 *
 * <ul>
 *   <li>{@link #getAllEvents()} — 전체 행사 목록을 페이지 단위로 조회하는 기능 검증</li>
 *   <li>{@link #searchEventsByTitle()} — 특정 키워드를 포함하는 행사 제목 검색 기능 검증</li>
 *   <li>{@link #getEventsByCategory()} — 카테고리명 기반의 행사 목록 조회 기능 검증</li>
 *   <li>{@link #getEventDetail()} — 단일 행사 상세 조회 기능 검증</li>
 * </ul>
 *
 * <p>테스트 데이터는 {@link org.springframework.test.util.ReflectionTestUtils}를 사용하여
 * 엔티티 내부 필드 값(식별자 등)을 직접 주입합니다.
 * 각 테스트는 {@link org.assertj.core.api.Assertions}를 사용하여 결과 객체의 유효성을 확인합니다.</p>
 */
@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    private Category category;
    private LocalGov localGov;
    private Event event;

    @BeforeEach
    void setUp() {
        category = new Category("문화행사");
        ReflectionTestUtils.setField(category, "categoryNo", 1L);

        localGov = new LocalGov("서울특별시");

        event = new Event(localGov, category, "진주유등축제","경상남도 진주시",
                LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 13),
                LocalTime.of(10, 0), LocalTime.of(23, 0),
                "https://example2.com", "남강둔치", "진주시청", "관리자", "진주의 대표 축제");

        ReflectionTestUtils.setField(event, "eventNo", 1L);
    }

    @Test
    @DisplayName("전체 행사 목록을 페이지 단위로 조회")
    void getAllEvents() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Event> eventPage = new PageImpl<>(List.of(event));
        when(eventRepository.findAll(any(Pageable.class))).thenReturn(eventPage);

        Page<EventListResponse> result = eventService.getAllEvents(pageable);

        verify(eventRepository, times(1)).findAll(any(Pageable.class));

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().eventTitle()).isEqualTo("진주유등축제");

    }

    @Test
    @DisplayName("특정 키워드가 들어간 행사 페이지 단위로 조회")
    void searchEventsByTitle() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Event> eventPage = new PageImpl<>(Collections.singletonList(event));

        when(eventRepository.findByEventTitleContaining(anyString(), any(Pageable.class)))
                .thenReturn(eventPage);

        Page<EventListResponse> result = eventService.searchEventsByTitle("유등", pageable);

        verify(eventRepository, times(1)).findByEventTitleContaining(anyString(), any(Pageable.class));

        assertThat(result).isNotNull();
        assertThat(result.getContent().getFirst().eventTitle()).contains("유등");

    }

    @Test
    @DisplayName("특정 카테고리에 속한 행사 목록을 페이지 단위로 조회")
    void getEventsByCategory() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<Event> eventPage = new PageImpl<>(Collections.singletonList(event));

        when(categoryRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(category));

        when(eventRepository.findByCategory_CategoryNo(anyLong(), any(Pageable.class)))
                .thenReturn(eventPage);


        Page<EventListResponse> result = eventService.getEventsByCategory(1L, pageable);

        verify(categoryRepository, times(1)).findById(anyLong());
        verify(eventRepository, times(1)).findByCategory_CategoryNo(anyLong(),any(Pageable.class));

        assertThat(result).isNotNull();
        assertThat(result.getContent().getFirst().eventTitle()).isEqualTo("진주유등축제");

    }

    @Test
    @DisplayName("단일 행사 상세 정보 조회")
    void getEventDetail() {
        when(eventRepository.findByEventNo(anyLong())).thenReturn(Optional.of(event));

        EventDetailResponse result = eventService.getEventDetail(1L);

        assertThat(result).isNotNull();
        assertThat(result.eventTitle()).contains("유등");

    }
}