package com.cu2mber.eventservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173") // 프론트 개발 서버 허용(지금은 로컬 테스트)
                .allowedMethods("*")                  // 모든 HTTP 메서드 허용
                .allowCredentials(true);
    }
}
