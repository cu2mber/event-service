package com.cu2mber.eventservice.event.dto;

import com.cu2mber.eventservice.event.domain.Event;
import io.swagger.v3.oas.annotations.media.Schema;

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
        @Schema(name = "행정구역")
        String localDistrict,
        @Schema(name = "지역이름")
        String localName,
        @Schema(name = "축제성격")
        String categoryName,
        @Schema(name = "축제이름")
        String eventTitle,
        @Schema(name = "개최시작일자")
        LocalDate eventStartDate,
        @Schema(name = "개최종료일자")
        LocalDate eventEndDate,
        @Schema(name = "운영시작시간")
        LocalTime eventStartTime,
        @Schema(name = "운영종료시간")
        LocalTime eventEndTime,
        @Schema(name = "관련누리집")
        String eventUrl,
        @Schema(name = "축제장소")
        String eventSpot,
        @Schema(name = "주최/주관기관")
        String eventHost,
        @Schema(name = "문의")
        String eventInquiry,
        @Schema(name = "축제설명")
        String eventDescription,
        @Schema(name = "축제이미지")
        String eventImageUrl
) {
    public static EventDetailResponse from(Event event) {
        return new EventDetailResponse(
                event.getLocalGov().getLocalDistrict(),
                event.getLocalGov().getLocalName(),
                event.getCategory().getCategoryName(),
                event.getEventTitle(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.getEventStartTime(),
                event.getEventEndTime(),
                event.getEventUrl(),
                event.getEventSpot(),
                event.getEventHost(),
                event.getEventInquiry(),
                event.getEventDescription(),
                event.getEventImageUrl()
        );
    }
}
