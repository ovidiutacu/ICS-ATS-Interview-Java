package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.repository.CalendarRepository;
import com.thales.interview.domain.service.DeleteCalendarService;
import com.thales.interview.domain.service.DeleteEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteCalendarServiceImpl implements DeleteCalendarService {

    private final CalendarRepository calendarRepository;
    private final DeleteEventService deleteEventService;

    @Override
    public void deleteAllCalendars() {

        calendarRepository.deleteAll();
        deleteEventService.deleteAllEvents();
    }

    @Override
    public void deleteCalendarById(UUID id) throws IllegalArgumentException {

        if (id == null) {
            throw new IllegalArgumentException("Can't delete a calendar with null id");
        }
        calendarRepository.findById(id).ifPresent(calendar -> {
            calendarRepository.delete(calendar);
            deleteEventService.deleteEventsByCalendarId(id);
        });
    }

    @Override
    public void deleteCalendarByName(String name) throws IllegalArgumentException {

        if (name == null) {
            throw new IllegalArgumentException("Can't delete a calendar with null name");
        }
        calendarRepository.findByName(name).ifPresent(calendar -> {
            calendarRepository.delete(calendar);
            deleteEventService.deleteEventsByCalendarId(calendar.id());
        });
    }
}
