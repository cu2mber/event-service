package com.cu2mber.eventservice.event.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

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
