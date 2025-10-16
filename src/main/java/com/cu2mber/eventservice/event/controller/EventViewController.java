package com.cu2mber.eventservice.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventViewController {
    // 행사페이지로 이동하는 메소드(페이지 번호 이동도 가능할지?)
    @GetMapping
    public String forwardEventPage(){
        return "/index.html";
    }

    // 행사 소개 페이지(행사1개) 페이지로 가는 메소드
    @GetMapping("/{no}")
    public String forwardEventDetailPage(){
        return "forward:/detail.html";
    }

}
