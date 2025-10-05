package com.cu2mber.eventservice.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class EventListResponse {
    private Long eventNo;
    private Short localNo;
    private String eventTitle;
    private String eventAddress;
    private LocalDate eventStartDate;
    private LocalDate eventEndDate;
    private LocalTime eventStartTime;
    private LocalTime eventEndTime;
    private String eventInquiry;
}
