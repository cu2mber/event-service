package com.cu2mber.eventservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;


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

}
