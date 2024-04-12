package com.thales.interview.domain.service;

import com.thales.interview.domain.model.Calendar;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface ReadCalendarService {

    List<Calendar> getCalendars();

    Calendar getCalendarById(UUID id) throws IllegalArgumentException, NoSuchElementException;

    Calendar getCalendarByName(String name) throws IllegalArgumentException, NoSuchElementException;
}
