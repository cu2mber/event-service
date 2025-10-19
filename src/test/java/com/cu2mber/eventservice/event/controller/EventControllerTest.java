package com.cu2mber.eventservice.event.controller;

import com.cu2mber.eventservice.category.domain.Category;
import com.cu2mber.eventservice.event.dto.EventDetailResponse;
import com.cu2mber.eventservice.event.dto.EventListResponse;
import com.cu2mber.eventservice.event.service.EventService;
import com.cu2mber.eventservice.localgov.domain.LocalGov;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code EventControllerTest} 클래스는 {@link EventController}의 웹 계층을 단위 테스트하기 위한 클래스입니다.
 *
 * <p>
 *     이 테스트는 Spring MVC 환경을 시뮬레이션하기 위해 {@link WebMvcTest}와 {@link MockMvc}를 사용하며,
 *     컨트롤러의 요청-응답 흐름이 기대한 대로 동작하는지를 검증합니다.
 *</p>
 *
 * <p>
 *     {@link EventService}는 실제 빈 대신 {@link org.springframework.test.context.bean.override.mockito.MockitoBean}을 사용하여
 *     Mock 객체로 대체되며, 서비스 로직의 결과를 가상으로 주입함으로써 컨트롤러 단위의 동작만을 검증합니다.
 * </p>
 *
 * <ul>
 *   <li>{@link #getAllEvents()} — 행사 목록 조회 요청 시 200 응답 코드와 JSON 구조를 검증합니다.</li>
 *   <li>{@link #getEventDetail()} — 특정 행사 조회 요청 시 응답 데이터의 필드 값과 상태 코드를 검증합니다.</li>
 * </ul>
 *
 * <p>이 테스트 클래스는 보안 필터를 비활성화하기 위해 {@code @AutoConfigureMockMvc(addFilters = false)}를 사용하며,
 * 순수하게 {@code EventController}의 요청 처리 로직만을 테스트하는 것을 목표로 합니다.</p>
 */

@WebMvcTest(EventController.class)
@AutoConfigureMockMvc(addFilters = false)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    private final LocalGov localGov = new LocalGov("서울특별시");
    private final Category category = new Category("문화행사");

    @Test
    @DisplayName("행사 목록 조회 요청시 200 응답")
    void getAllEvents() throws Exception{
        EventListResponse mockResponse = new EventListResponse(
                1L,
                localGov,
                category,
                "테스트 행사",
                "서울특별시",
                LocalDate.now(),
                LocalDate.now(),
                LocalTime.of(10,0),
                LocalTime.of(11, 0),
                "테스트 문의"
        );
        Page<EventListResponse> mockPage = new PageImpl<>(List.of(mockResponse));

        when(eventService.getAllEvents(any(Pageable.class)))
                .thenReturn(mockPage);

        mockMvc.perform(get("/events")
                            .param("page", "0")
                            .param("size", "5"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content[0].eventNo").value(1))
                    .andExpect(jsonPath("$.content[0].eventTitle").value("테스트 행사"));


        verify(eventService, times(1)).getAllEvents(any(Pageable.class));

    }

    @Test
    @DisplayName("특정 행사 하나를 조회")
    void getEventDetail() throws Exception{
        EventDetailResponse mockResponse = new EventDetailResponse(1L,
                localGov,
                category,
                "테스트 행사",
                LocalDate.now(),
                LocalDate.now(),
                LocalTime.of(10,0),
                LocalTime.of(11,0),
                "https://github.com/cu2mber",
                "테스트 장소",
                "테스트 주최기관",
                "테스트 문의",
                "설명"
        );

        when(eventService.getEventDetail(anyLong()))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventTitle").value("테스트 행사"));

        verify(eventService, times(1)).getEventDetail(anyLong());
    }
}