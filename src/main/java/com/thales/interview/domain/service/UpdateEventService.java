package com.thales.interview.domain.service;

import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.model.UpdateEventDateRequest;
import com.thales.interview.domain.model.UpdateEventNameRequest;

public interface UpdateEventService {

    Event updateEventDate(UpdateEventDateRequest request) throws IllegalArgumentException;

    Event updateEventName(UpdateEventNameRequest request) throws IllegalArgumentException;
}
