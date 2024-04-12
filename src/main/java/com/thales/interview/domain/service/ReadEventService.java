package com.thales.interview.domain.service;

import com.thales.interview.domain.model.Event;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface ReadEventService {

    List<Event> getEvents();

    Event getEventById(UUID id) throws IllegalArgumentException, NoSuchElementException;

    List<Event> getEventsByCalendarId(UUID calendarId) throws IllegalArgumentException;
}
