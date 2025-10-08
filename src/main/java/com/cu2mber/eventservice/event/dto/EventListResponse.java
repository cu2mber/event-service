package com.cu2mber.eventservice.event.dto;

import com.cu2mber.eventservice.event.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * {@code EventListResponse}는 {@link com.cu2mber.eventservice.event.domain.Event} 엔티티의
 * 목록용 요약 정보를 표현하는 응답 DTO입니다.
 *
 * <p>
 * 본 클래스는 행사 게시판 목록 조회 API 응답에 사용되며,
 * {@link #from(Event)} 정적 팩토리 메서드를 통해 {@link Event} 엔티티를
 * {@code EventListResponse} 형태로 변환합니다.
 * </p>
 *
 * <p>
 * DTO는 불변(immutable)하게 설계되었으며,
 * 모든 필드는 {@code final}로 선언되어 객체 생성 후 수정이 불가능합니다.
 * </p>
 */
@Getter
@AllArgsConstructor
public class EventListResponse {
    private Long eventNo;
    private Short localNo;
    private Long categoryNo;
    private String eventTitle;
    private String eventAddress;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private String eventInquiry;

    /**
     * {@link Event} 엔티티를 {@link EventListResponse} DTO로 변환합니다.
     *
     * <p>
     * 이 메서드는 서비스 계층에서 {@code Page<Event>} 형태로 조회된 엔티티를
     * {@code Page<EventListResponse>} 형태로 변환할 때 사용됩니다.
     * <br>
     * 변환 로직을 DTO 내부에 두어, 서비스 계층이 엔티티 구조에 직접 의존하지 않도록 합니다.
     * 즉, 엔티티 → DTO 변환 책임을 명확히 분리하여 재사용성과 유지보수성을 높입니다.
     * </p>
     *
     */
    public static EventListResponse from(Event event){
        return new EventListResponse(
                event.getEventNo(),
                event.getLocalNo(),
                event.getCategoryNo(),
                event.getEventTitle(),
                event.getEventAddress(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.getEventStartTime(),
                event.getEventEndTime(),
                event.getEventInquiry()
        );
    }

}
