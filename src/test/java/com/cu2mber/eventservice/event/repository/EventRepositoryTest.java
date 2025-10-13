package com.cu2mber.eventservice.event.repository;

import com.cu2mber.eventservice.category.domain.Category;
import com.cu2mber.eventservice.category.repository.CategoryRepository;
import com.cu2mber.eventservice.event.domain.Event;
import com.cu2mber.eventservice.localgov.domain.LocalGov;
import com.cu2mber.eventservice.localgov.repository.LocalGovRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
@ActiveProfiles("test")
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LocalGovRepository localGovRepository;

    @BeforeEach
    void setUp() {
        Category category1 = categoryRepository.save(new Category("문화생활"));
        Category category2 = categoryRepository.save(new Category("체험"));

        LocalGov localGov1 = localGovRepository.save(new LocalGov("서울특별시"));
        LocalGov localGov2 = localGovRepository.save(new LocalGov("경상남도", "진주시", "055-111-1111", "jinju@jinju.com"));

        eventRepository.save(new Event(localGov1, category1, "고메 잇 강남 서울야장", "서울특별시",
                LocalDate.of(2025, 8, 17), LocalDate.of(2025, 8, 31),
                LocalTime.of(10, 0), LocalTime.of(23, 30),
                "https://example.com", "서울 코엑스 동측광장", "코엑스", "관리자", "레트로 감성 행사"));

        eventRepository.save(new Event(localGov2, category2, "진주유등축제", "경상남도 진주시",
                LocalDate.of(2025, 10, 1), LocalDate.of(2025, 10, 13),
                LocalTime.of(10, 0), LocalTime.of(23, 0),
                "https://example2.com", "남강둔치", "진주시청", "관리자", "진주의 대표 축제"));

        eventRepository.save(new Event(localGov1, category1, "서울 불꽃축제", "서울특별시 여의도 한강공원2", LocalDate.of(2025, 10, 5), LocalDate.of(2025, 10, 5),
                LocalTime.of(19, 0),LocalTime.of(21, 0),
                "https://hanwhafireworks.co.kr/",
                "여의도 한강공원", "한화그룹2", "02-789-1234",
                "한강 위에서 펼쳐지는 대규모 불꽃쇼로, 서울의 가을을 대표하는 축제이다."));
    }

    @Test
    @DisplayName("모든 행사 페이징 조회 테스트")
    void findAll() {
        Page<Event> events = eventRepository.findAll(Pageable.ofSize(3));

        assertThat(events.getTotalElements()).isEqualTo(3);
        assertThat(events.getContent()).hasSize(3);

    }

    @Test
    @DisplayName("행사명으로 조회 테스트")
    void findByEventTitleContaining() {
        var results = eventRepository.findByEventTitleContaining("서울", Pageable.ofSize(2));
        assertThat(results).hasSize(2);
        assertThat(results.getContent().getFirst().getEventTitle()).contains("서울");
    }

    @Test
    @DisplayName("카테고리별 조회 테스트")
    void findByCategoryNo() {
    }

    @Test
    @DisplayName("행사 고유 번호 조회 테스트")
    void findByEventNo() {
    }
}