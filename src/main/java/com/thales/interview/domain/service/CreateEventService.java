package com.thales.interview.domain.service;

import com.thales.interview.domain.model.CreateEventsRequest;
import com.thales.interview.domain.model.Event;

import java.util.List;

public interface CreateEventService {

    List<Event> createEvents(CreateEventsRequest createEventsRequest) throws IllegalArgumentException;
}
