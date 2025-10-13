package com.cu2mber.eventservice.event.repository;

import com.cu2mber.eventservice.event.domain.Event;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Slf4j
@ActiveProfiles("test")
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void findAll() {
        Page<Event> events = eventRepository.findAll(Pageable.ofSize(5));

        Assertions.assertEquals(5, events.getTotalElements());
    }

    @Test
    void findByEventTitleContaining() {
    }

    @Test
    void findByCategoryNo() {
    }

    @Test
    void findByEventNo() {
    }
}