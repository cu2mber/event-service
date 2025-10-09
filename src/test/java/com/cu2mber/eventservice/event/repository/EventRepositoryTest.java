package com.cu2mber.eventservice.event.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    void findAll() {
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