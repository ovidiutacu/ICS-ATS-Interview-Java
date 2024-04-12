package com.thales.interview.domain.service;

import com.thales.interview.domain.exception.ElementAlreadyExistingException;
import com.thales.interview.domain.model.Calendar;

public interface CreateCalendarService {

    Calendar createCalendar(Calendar calendar) throws IllegalArgumentException, ElementAlreadyExistingException;
}
