package com.thales.interview.domain.service;

import java.util.UUID;

public interface DeleteEventService {

    void deleteAllEvents();

    void deleteEventById(UUID id) throws IllegalArgumentException;

    void deleteEventsByCalendarId(UUID calendarId) throws IllegalArgumentException;
}
