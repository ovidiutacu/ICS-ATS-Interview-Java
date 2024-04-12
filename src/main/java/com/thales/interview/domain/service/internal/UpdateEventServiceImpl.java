package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.model.UpdateEventDateRequest;
import com.thales.interview.domain.model.UpdateEventNameRequest;
import com.thales.interview.domain.repository.EventRepository;
import com.thales.interview.domain.service.ReadEventService;
import com.thales.interview.domain.service.UpdateEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UpdateEventServiceImpl implements UpdateEventService {

    private final ReadEventService readEventService;
    private final EventRepository eventRepository;

    @Override
    public Event updateEventDate(UpdateEventDateRequest request) throws IllegalArgumentException {
        validateUpdateDateRequest(request);
        Event oldEvent = readEventService.getEventById(request.eventId());
        Event updatedEvent = oldEvent.toBuilder().date(request.newDate()).build();
        return eventRepository.save(updatedEvent);
    }

    private void validateUpdateDateRequest(UpdateEventDateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Can't update the date of an event with a null request");
        }
        if (request.eventId() == null) {
            throw new IllegalArgumentException("Can't update the date of an event with a null event id");
        }
        if (request.newDate() == null) {
            throw new IllegalArgumentException("Can't update the date of an event with a null new date");
        }
        if (!request.newDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Can't update the date of an event with a past or current date");
        }
    }

    @Override
    public Event updateEventName(UpdateEventNameRequest request) throws IllegalArgumentException {
        validateUpdateNameRequest(request);
        Event oldEvent = readEventService.getEventById(request.eventId());
        Event updatedEvent = oldEvent.toBuilder().name(request.newName()).build();
        return eventRepository.save(updatedEvent);
    }

    private void validateUpdateNameRequest(UpdateEventNameRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Can't update the name of an event with a null request");
        }
        if (request.eventId() == null) {
            throw new IllegalArgumentException("Can't update the name of an event with a null event id");
        }
        if (request.newName() == null) {
            throw new IllegalArgumentException("Can't update the name of an event with a null new name");
        }
    }
}
