package com.cu2mber.eventservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * {@code SecurityConfig} 클래스는 Spring Security 필터 체인을 구성하는 설정 클래스입니다.
 *
 * <p>
 *     본 설정은 event-service를 "공개 조회(read-only) 서비스"로 운영하기 위해 설계되었습니다.
 * </p>
 *
 * <h3>주요 설정 내용</h3>
 * <ul>
 *   <li><b>CSRF 비활성화:</b> REST API용 서비스이므로 CSRF 보호는 비활성화합니다.</li>
 *   <li><b>GET 요청 허용:</b> {@code /api/**} 경로의 GET 요청은 인증 없이 접근할 수 있습니다.</li>
 *   <li><b>그 외 요청 차단:</b> POST, PUT, DELETE 등 모든 다른 HTTP 요청은 접근이 차단됩니다.</li>
 * </ul>
 *
 * <p>
 *     추후 JWT 인증 또는 관리자용 API를 추가할 경우,
 *     {@code authorizeHttpRequests()} 부분을 수정하여 인증이 필요한 경로를 지정하면 됩니다.
 * </p>
 *
 */
@Configuration
public class SecurityConfig {

    /**
     * Spring Security 필터 체인을 구성합니다.
     *
     * <p>
     *     GET 요청은 공개(read-only)로 허용하며, 나머지 요청은 차단하는 설정입니다.
     * </p>
     *
     * @param http {@link HttpSecurity} 객체로, HTTP 요청 보안을 구성합니다.
     * @return {@link SecurityFilterChain} 구성된 보안 필터 체인 인스턴스
     * @throws Exception 보안 설정 중 예외 발생 시 던져집니다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Swagger 관련 경로 허용
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().denyAll()
                )
                .build();
    }
}
