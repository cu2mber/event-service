package com.cu2mber.eventservice.event.service.impl;

import com.cu2mber.eventservice.event.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventService eventService;

    @Test
    @DisplayName("전체 행사 목록을 페이지 단위로 조회")
    void getAllEvents() {

    }

    @Test
    @DisplayName("특정 키워드가 들어간 행사 페이지 단위로 조회")
    void searchEventsByTitle() {
    }

    @Test
    @DisplayName("특정 카테고리에 속한 행사 목록을 페이지 단위로 조회")
    void getEventsByCategory() {
    }

    @Test
    @DisplayName("단일 행사 상세 정보 조회")
    void getEventDetail() {
    }
}