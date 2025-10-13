package com.cu2mber.eventservice.event.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * {@code Event} 엔티티는 행사 정보를 데이터베이스에 저장하기 위한 도메인 모델입니다.
 *
 * <p>
 *     본 클래스는 지역, 카테고리, 행사명 등 식별 가능한 주요 정보를 포함하며,
 *     행사 일정·시간·장소·주최 정보·설명 등의 상세 데이터 조회 용입니다.
 * </p>
 *
 *
 */
@Getter
@Entity
@NoArgsConstructor
@Table(name = "events",
        uniqueConstraints = @UniqueConstraint(columnNames = {"event_no", "local_no", "category_no"}))
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventNo; // bigint

    private Short localNo; // smallint

    private Long categoryNo; // bigint

    private String eventTitle;

    private String eventAddress;

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

    private LocalDateTime createdAt;

    public Event(Short localNo, Long categoryNo, String eventTitle,
                 String eventAddress, LocalDate eventStartDate, LocalDate eventEndDate,
                 LocalTime eventStartTime, LocalTime eventEndTime, String eventUrl,
                 String eventSpot, String eventFee, String eventHost,
                 String eventInquiry, String eventDescription) {
        this.localNo = localNo;
        this.categoryNo = categoryNo;
        this.eventTitle = eventTitle;
        this.eventAddress = eventAddress;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventUrl = eventUrl;
        this.eventSpot = eventSpot;
        this.eventFee = eventFee;
        this.eventHost = eventHost;
        this.eventInquiry = eventInquiry;
        this.eventDescription = eventDescription;
    }
}
