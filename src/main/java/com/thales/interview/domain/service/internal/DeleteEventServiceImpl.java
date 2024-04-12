package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.repository.EventRepository;
import com.thales.interview.domain.service.DeleteEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteEventServiceImpl implements DeleteEventService {

    private final EventRepository eventRepository;

    @Override
    public void deleteAllEvents() {

        eventRepository.deleteAll();
    }

    @Override
    public void deleteEventById(UUID id) throws IllegalArgumentException {

        if (id == null) {
            throw new IllegalArgumentException("Can't delete an event with null id");
        }
        eventRepository.findById(id).ifPresent(eventRepository::delete);
    }

    @Override
    public void deleteEventsByCalendarId(UUID calendarId) throws IllegalArgumentException {

        if (calendarId == null) {
            throw new IllegalArgumentException("Can't delete events with null calendarId");
        }
        eventRepository.findByCalendarId(calendarId).forEach(eventRepository::delete);
    }
}
