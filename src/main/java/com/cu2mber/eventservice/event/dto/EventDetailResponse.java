package com.cu2mber.eventservice.event.dto;

import com.cu2mber.eventservice.event.domain.Event;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * {@code EventDetailResponse}는 단일 {@link Event} 엔티티의
 * 상세 정보를 클라이언트에 전달하기 위한 응답 DTO입니다.
 *
 * <p>
 * 본 클래스는 행사 상세 조회 API 응답에 사용되며,
 * {@link #from(Event)} 정적 팩토리 메서드를 통해 {@link Event} 엔티티로부터 데이터를 변환합니다.
 * </p>
 *
 * <p>
 * DTO는 {@code record}로 정의되어 불변(immutable)합니다.
 * 모든 필드는 자동으로 {@code final}이며, 생성 이후 수정이 불가능합니다.
 * </p>
 */
public record EventDetailResponse(
        Long eventNo,
        Short localNo,
        Long categoryNo,
        String eventTitle,
        LocalDate eventStartDate,
        LocalDate eventEndDate,
        LocalTime eventStartTime,
        LocalTime eventEndTime,
        String eventUrl,
        String eventSpot,
        String eventFee,
        String eventHost,
        String eventInquiry,
        String eventDescription
) {
    public static EventDetailResponse from(Event event) {
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
