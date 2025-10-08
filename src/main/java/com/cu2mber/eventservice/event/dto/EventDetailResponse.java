package com.cu2mber.eventservice.event.dto;

import com.cu2mber.eventservice.event.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * {@code EventDetailResponse}는 단일 {@link com.cu2mber.eventservice.event.domain.Event} 엔티티의
 * 상세 정보를 클라이언트에 전달하기 위한 응답 DTO입니다.
 *
 * <p>
 * 본 클래스는 행사 상세 조회 API 응답에 사용되며,
 * {@link #from(Event)} 정적 팩토리 메서드를 통해 {@link Event} 엔티티로부터 데이터를 변환합니다.
 * </p>
 *
 * <p>
 * DTO는 불변(immutable)하게 설계되었으며,
 * 모든 필드는 {@code final}로 선언되어 객체 생성 후 수정이 불가능합니다.
 * </p>
 */

@Getter
@AllArgsConstructor
public class EventDetailResponse {
    private Long eventNo;
    private Short localNo;
    private Long categoryNo;
    private String eventTitle;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private String eventUrl;
    private String eventSpot;
    private String eventFee;
    private String eventHost;
    private String eventInquiry;
    private String eventDescription;

    public static EventDetailResponse from(Event event){
        return new EventDetailResponse(
                event.getEventNo(),
                event.getLocalNo(),
                event.getCategoryNo(),
                event.getEventTitle(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.getEventStartTime(),
                event.getEventEndTime(),
                event.getEventUrl(),
                event.getEventSpot(),
                event.getEventFee(),
                event.getEventHost(),
                event.getEventInquiry(),
                event.getEventDescription()
        );
    }
}
