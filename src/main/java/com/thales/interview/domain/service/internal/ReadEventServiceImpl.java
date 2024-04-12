package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.repository.EventRepository;
import com.thales.interview.domain.service.ReadEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ReadEventServiceImpl implements ReadEventService {

    private final EventRepository eventRepository;

    @Override
    public List<Event> getEvents() {

        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(UUID id) throws IllegalArgumentException, NoSuchElementException {

        if (id == null) {
            throw new IllegalArgumentException("Can't get an event with null id");
        }
        Optional<Event> event = eventRepository.findById(id);
        if (event.isEmpty()) {
            throw new NoSuchElementException(String.format("Event with id [%s] not found", id));
        }
        return event.get();
    }

    @Override
    public List<Event> getEventsByCalendarId(UUID calendarId) throws IllegalArgumentException {

        if (calendarId == null) {
            throw new IllegalArgumentException("Can't get events with null calendarId");
        }
        return eventRepository.findByCalendarId(calendarId);
    }
}
