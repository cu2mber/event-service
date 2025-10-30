package com.cu2mber.eventservice.category.repository;

import com.cu2mber.eventservice.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@code CategoryRepository} 인터페이스는 {@link Category} 엔티티에 대한
 * 데이터 접근 계층(Repository)을 정의합니다.
 *
 * <p>
 *     Spring Data JPA의 {@link JpaRepository}를 상속받아 기본적인 CRUD(Create, Read, Update, Delete)
 *     기능과 페이징, 정렬 기능을 제공합니다.
 * </p>
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
