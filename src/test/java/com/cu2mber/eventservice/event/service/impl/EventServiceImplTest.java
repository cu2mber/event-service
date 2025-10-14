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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
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
        category = new Category("문화생활");
        localGov = new LocalGov("서울특별시");

        event = new Event(localGov, category, "진주유등축제","경상남도 진주시",
                LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 13),
                LocalTime.of(10, 0), LocalTime.of(23, 0),
                "https://example2.com", "남강둔치", "진주시청", "관리자", "진주의 대표 축제");
    }

    @Test
    @DisplayName("전체 행사 목록을 페이지 단위로 조회")
    void getAllEvents() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Event> eventPage = new PageImpl<>(List.of(event));
        when(eventRepository.findAll(pageable)).thenReturn(eventPage);

        Page<EventListResponse> result = eventService.getAllEvents(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().getFirst().eventTitle()).isEqualTo("진주유등축제");

    }

    @Test
    @DisplayName("특정 키워드가 들어간 행사 페이지 단위로 조회")
    void searchEventsByTitle() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Event> eventPage = new PageImpl<>(Collections.singletonList(event));

        when(eventRepository.findByEventTitleContaining("유등", pageable))
                .thenReturn(eventPage);

        Page<EventListResponse> result = eventService.searchEventsByTitle("유등", pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent().getFirst().eventTitle()).contains("유등");

    }

    @Test
    @DisplayName("특정 카테고리에 속한 행사 목록을 페이지 단위로 조회")
    void getEventsByCategory() {
        Pageable pageable = PageRequest.of(0, 5);

        Page<Event> eventPage = new PageImpl<>(Collections.singletonList(event));

        when(categoryRepository.findByCategoryName("문화행사"))
                .thenReturn(category);

        when(eventRepository.findByCategory_CategoryNo(1L, pageable))
                .thenReturn(eventPage);

        Page<EventListResponse> result = eventService.getEventsByCategory("문화행사", pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent().getFirst().category()).isEqualTo("문화행사");
    }

    @Test
    @DisplayName("단일 행사 상세 정보 조회")
    void getEventDetail() {
        when(eventRepository.findByEventNo(1L)).thenReturn(Optional.of(event));

        EventDetailResponse result = eventService.getEventDetail(1L);

        assertThat(result).isNotNull();
        assertThat(result.eventNo()).isEqualTo(1L);
        assertThat(result.eventTitle()).contains("유등");

    }
}