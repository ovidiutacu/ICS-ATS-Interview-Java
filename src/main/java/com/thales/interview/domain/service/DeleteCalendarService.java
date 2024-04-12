package com.thales.interview.domain.service;

import java.util.UUID;

public interface DeleteCalendarService {

    void deleteAllCalendars();

    void deleteCalendarById(UUID id) throws IllegalArgumentException;

    void deleteCalendarByName(String name) throws IllegalArgumentException;
}
