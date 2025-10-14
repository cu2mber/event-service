package com.cu2mber.eventservice.event.repository;

import com.cu2mber.eventservice.category.domain.Category;
import com.cu2mber.eventservice.event.domain.Event;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * {@code EventRepository}는 행사(Event) 엔티티에 대한 데이터 접근 기능을 제공합니다.
 * <p>
 * Spring Data JPA를 사용하여 데이터베이스에서 행사 정보를 조회, 검색, 분류합니다.
 * 기본 CRUD 기능은 {@link JpaRepository}를 상속받아 제공합니다.
 * </p>
 */
public interface EventRepository extends JpaRepository<Event, Long> {

    /**
     * 모든 행사를 페이지 단위로 조회합니다.
     *
     * @param pageable 페이징 및 정렬 정보를 포함한 {@link Pageable} 객체
     * @return {@link Event} 엔티티 목록이 포함된 {@link Page}
     */
    @NonNull
    Page<Event> findAll(@NonNull Pageable pageable);

    /**
     * 행사 제목에 특정 키워드가 포함된 축제 목록을 페이지 단위로 조회합니다.
     * <p>
     * 부분 일치 검색(contains) 방식으로 제목을 검색합니다.
     * </p>
     *
     * @param keyword  검색할 키워드 (제목에 포함될 문자열)
     * @param pageable 페이징 및 정렬 정보를 포함한 {@link Pageable} 객체
     * @return 조건에 맞는 {@link Event} 목록이 포함된 {@link Page}
     */
    @Query("SELECT e FROM Event e WHERE e.eventTitle LIKE %:keyword%")
    Page<Event> findByEventTitleContaining(@Param("keyword")String keyword, Pageable pageable);

    /**
     * 특정 카테고리 번호에 속하는 행사 목록을 페이지 단위로 조회합니다.
     *
     * @param categoryNo 카테고리 번호
     * @param pageable   페이징 및 정렬 정보를 포함한 {@link Pageable} 객체
     * @return 해당 카테고리의 {@link Event} 목록이 포함된 {@link Page}
     */
    Page<Event> findByCategory_CategoryNo(Long categoryNo, Pageable pageable);

    /**
     * 행사 고유 번호(eventNo)를 기준으로 단일 행사 정보를 조회합니다.
     *
     * @param eventNo 행사 고유 번호
     * @return 해당 번호에 해당하는 {@link Event} 엔티티, 존재하지 않으면 {@code null}
     */
    Optional<Event> findByEventNo(Long eventNo);
}
