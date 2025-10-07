package com.cu2mber.eventservice.event.dto;

import com.cu2mber.eventservice.event.domain.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

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
