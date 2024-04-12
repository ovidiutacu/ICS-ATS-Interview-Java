package com.thales.interview.domain.repository;

import com.thales.interview.domain.model.Event;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {

    List<Event> findAll();

    Optional<Event> findById(UUID id) throws IllegalArgumentException;

    List<Event> findByCalendarId(UUID calendarId) throws IllegalArgumentException;

    Event save(Event event) throws IllegalArgumentException;

    void delete(Event event) throws IllegalArgumentException;

    void deleteAll();
}
