package com.cu2mber.eventservice.event.dto;

import com.cu2mber.eventservice.event.domain.Event;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * {@code EventListResponse}는 {@link Event} 엔티티의
 * **목록용 요약 정보**를 표현하는 응답 DTO입니다.
 *
 * <p>
 * 본 DTO는 행사 게시판의 **목록 조회 API 응답**에 사용되며,
 * 각 행사에 대한 주요 정보를 클라이언트에 전달합니다.
 * </p>
 *
 * <p>
 * {@link #from(Event)} 정적 팩토리 메서드를 통해
 * {@link Event} 엔티티로부터 본 DTO로 변환됩니다.
 * 이 방식을 사용함으로써, 서비스 계층이 엔티티의 내부 구조에 직접 의존하지 않고
 * **엔티티 → DTO 변환 책임을 명확히 분리**하여 유지보수성을 높입니다.
 * </p>
 *
 * <p>
 * 본 클래스는 {@code record}로 정의되어 있으며, 모든 필드는 자동으로 {@code final}입니다.
 * 따라서 생성 이후 값이 변경되지 않는 **완전한 불변(immutable) 객체**입니다.
 * </p>
 *
 * @see Event
 * @see #from(Event)
 */
public record EventListResponse(
        @Schema(name = "행사번호")
        Long eventNo,
        @Schema(name = "축제이름")
        String eventTitle,
        @Schema(name = "축제설명")
        String eventDescription,
        @Schema(name = "개최지역")
        String eventAddress,
        @Schema(name = "개최시작일자")
        LocalDate eventStartDate,
        @Schema(name = "개최종료일자")
        LocalDate eventEndDate,
        @Schema(name = "운영시작시간")
        LocalTime eventStartTime,
        @Schema(name = "운영종료시간")
        LocalTime eventEndTime,
        @Schema(name = "문의")
        String eventInquiry
) {

    /**
     * {@link Event} 엔티티를 {@code EventListResponse}로 변환합니다.
     *
     * <p>
     * 본 메서드는 주로 서비스 계층에서 {@code Page<Event>} 형태의 엔티티 목록을
     * {@code Page<EventListResponse>} 형태로 매핑할 때 사용됩니다.
     * </p>
     *
     * <p>
     * 변환 로직을 DTO 내부에 위치시켜, 서비스 계층의 단순화와
     * 엔티티 의존성 최소화를 동시에 달성합니다.
     * </p>
     *
     * @param event 변환할 {@link Event} 엔티티
     * @return 변환된 {@link EventListResponse} 인스턴스
     */
    public static EventListResponse from(Event event) {
        return new EventListResponse(
                event.getEventNo(),
                event.getEventTitle(),
                event.getEventDescription(),
                event.getEventAddress(),
                event.getEventStartDate(),
                event.getEventEndDate(),
                event.getEventStartTime(),
                event.getEventEndTime(),
                event.getEventInquiry()
        );
    }
}
