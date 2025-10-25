package com.cu2mber.eventservice.event.repository;

import com.cu2mber.eventservice.category.domain.Category;
import com.cu2mber.eventservice.category.repository.CategoryRepository;
import com.cu2mber.eventservice.event.domain.Event;
import com.cu2mber.eventservice.localgov.domain.LocalGov;
import com.cu2mber.eventservice.localgov.repository.LocalGovRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.ActiveProfiles; // @ActiveProfiles

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@code EventRepositoryTest} 클래스는 {@link EventRepository}의 JPA 쿼리 메서드 동작을 검증하기 위한 테스트 클래스입니다.
 *
 * <p>
 *     이 테스트는 {@link DataJpaTest}를 사용하여 H2 인메모리 데이터베이스 환경에서
 *     리포지토리 계층만 로드하고, 실제 DB 없이 CRUD 및 페이징, 검색 기능을 검증합니다.
 *     {@link @TestInstance(TestInstance.Lifecycle.PER_CLASS)}를 통해 {@code @BeforeAll} 메서드에서
 *     초기 데이터를 설정하고 모든 테스트에서 재사용합니다.</p>
 *
 * <p>
 *     테스트 데이터로 {@link LocalGov}, {@link Category}, {@link Event} 엔티티를 저장하며,
 *     각 테스트 메서드는 해당 데이터를 기반으로 쿼리 결과를 검증합니다.
 * </p>
 *
 * <ul>
 *   <li>{@link #findAll()} — 모든 행사를 페이지 단위로 조회하는 기능 검증</li>
 *   <li>{@link #findByEventTitleContaining()} — 행사명에 특정 키워드가 포함된 이벤트 조회 기능 검증</li>
 *   <li>{@link #findByCategoryNo()} — 카테고리별 행사 조회 기능 검증</li>
 *   <li>{@link #findByEventNo()} — 존재하는 고유 번호로 단일 행사 조회 기능 검증</li>
 *   <li>{@link #existFindByEventNo()} — 존재하지 않는 고유 번호로 조회 시 빈 결과 검증</li>
 * </ul>
 *
 * <p>
 *     {@link @ActiveProfiles("test")}를 사용하여 테스트 전용 프로파일을 적용하며,
 *     페이징과 Optional 처리, 엔티티 관계 매핑 등을 포함한 리포지토리 동작을 통합적으로 검증합니다.
 * </p>
 */


@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LocalGovRepository localGovRepository;

    private Category category1;
    private Category category2;

    @BeforeAll
    void setUpOnce() {
        category1 = categoryRepository.save(new Category("문화생활"));
        category2 = categoryRepository.save(new Category("체험"));

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
        Long categoryNo = categoryRepository.findById(category1.getCategoryNo())
                .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"))
                .getCategoryNo();

        var results = eventRepository.findByCategory_CategoryNo(categoryNo, Pageable.ofSize(2));

        assertThat(results.getTotalElements()).isEqualTo(2);

    }

    @Test
    @DisplayName("존재하는 행사 고유 번호 조회 테스트")
    void findByEventNo() {

        LocalGov localGov = new LocalGov("서울특별시");
        localGovRepository.save(localGov);

        Category category = new Category("문화행사");
        categoryRepository.save(category);

        Event event = new Event(localGov, category, "고메 잇 강남 서울야장2", "서울특별시",
                LocalDate.of(2025, 8, 17), LocalDate.of(2025, 8, 31),
                LocalTime.of(10, 0), LocalTime.of(23, 30),
                "https://example.com", "서울 코엑스 동측광장", "코엑스", "관리자", "레트로 감성 행사");

        eventRepository.save(event);


        Optional<Event> found = eventRepository.findByEventNo(event.getEventNo());

        assertThat(found).isPresent();
        assertThat(found.get().getEventTitle()).isEqualTo("고메 잇 강남 서울야장2");
        assertThat(found.get().getCategory().getCategoryName()).isEqualTo("문화행사");

    }

    @Test
    @DisplayName("존재하지 않는 행사 고유 번호 조회 테스트")
    void existFindByEventNo(){
        Optional<Event> result = eventRepository.findByEventNo(999L);

        assertThat(result).isEmpty();
    }
}