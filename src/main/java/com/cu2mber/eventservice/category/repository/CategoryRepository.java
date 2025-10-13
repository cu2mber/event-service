package com.cu2mber.eventservice.category.repository;

import com.cu2mber.eventservice.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
