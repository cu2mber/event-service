package com.cu2mber.eventservice.category.domain;

import com.cu2mber.eventservice.event.domain.Event;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Category} 엔티티는 축제 카테고리(축제 성격)를 데이터베이스에 저장하기 위한 도메인 모델입니다.
 *
 * <p>
 *     본 클래스는 카테고리를 저장하기 위한 정보를 가지고 있습니다.
 * </p>
 */
@Getter
@Entity
@NoArgsConstructor
@Table(name = "events_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryNo; // bigint

    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<>();

    public Category(String categoryName){
        this.categoryName = categoryName;
    }
}
