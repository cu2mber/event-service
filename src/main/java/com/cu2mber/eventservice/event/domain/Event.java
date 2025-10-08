package com.cu2mber.eventservice.event.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * {@code Event} 엔티티는 축제·행사 정보를 데이터베이스에 저장하기 위한 도메인 모델입니다.
 *
 * <p>
 * 본 클래스는 지역, 카테고리, 행사명 등 식별 가능한 주요 정보를 포함하며,
 * 행사 일정·시간·장소·주최 정보·설명 등의 상세 데이터를 관리합니다.
 * {@link org.hibernate.annotations.CreationTimestamp}를 통해 등록 시점의 생성 일시({@code createdAt})가 자동 기록됩니다.
 * </p>
 *
 * <h2>매핑 정보</h2>
 * <ul>
 *     <li>테이블명: {@code events}</li>
 *     <li>고유 제약 조건: {@code (event_no, local_no, category_no)}</li>
 *     <li>기본키(PK): {@code eventNo}</li>
 * </ul>
 *
 * <h2>주요 컬럼 설명</h2>
 * <ul>
 *     <li>{@code eventNo} — 행사 고유 번호 (자동 증가)</li>
 *     <li>{@code localNo} — 지역 코드 번호</li>
 *     <li>{@code categoryNo} — 카테고리 코드 번호</li>
 *     <li>{@code eventTitle} — 행사명</li>
 *     <li>{@code eventAddress} — 행사 주소</li>
 *     <li>{@code eventStartDate}, {@code eventEndDate} — 행사 기간</li>
 *     <li>{@code eventStartTime}, {@code eventEndTime} — 행사 시작 및 종료 시각</li>
 *     <li>{@code eventUrl} — 행사 관련 외부 링크</li>
 *     <li>{@code eventSpot} — 행사 장소명</li>
 *     <li>{@code eventFee} — 참가비 정보</li>
 *     <li>{@code eventHost} — 주최 기관</li>
 *     <li>{@code eventInquiry} — 문의처</li>
 *     <li>{@code eventDescription} — 행사 상세 설명</li>
 *     <li>{@code createdAt} — 데이터 생성 시각 (자동 기록)</li>
 * </ul>
 *
 * <p>
 * 본 엔티티는 JPA 표준을 따르며, <strong>생성자 주입</strong>을 통해 필수 필드만 설정하고
 * {@code createdAt} 필드는 Hibernate가 자동 관리합니다.
 * </p>
 *
 * @since 1.0
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

    @Column(nullable = false)
    private Short localNo; // smallint

    @Column(nullable = false)
    private Long categoryNo; // bigint

    @Column(length = 255, nullable = false)
    private String eventTitle;

    @Column(length = 255)
    private String eventAddress;

    private LocalDate eventStartDate;

    private LocalDate eventEndDate;

    private LocalTime eventStartTime;

    private LocalTime eventEndTime;

    @Column(columnDefinition = "TEXT")
    private String eventUrl;

    @Column(length = 50)
    private String eventSpot;

    @Column(length = 50)
    private String eventFee;

    @Column(length = 50)
    private String eventHost;

    @Column(length = 50)
    private String eventInquiry;

    @Column(columnDefinition = "TEXT")
    private String eventDescription;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
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
