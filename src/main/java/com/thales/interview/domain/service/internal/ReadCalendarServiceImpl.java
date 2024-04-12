package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.repository.CalendarRepository;
import com.thales.interview.domain.service.ReadCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadCalendarServiceImpl implements ReadCalendarService {

    private final CalendarRepository calendarRepository;

    @Override
    public List<Calendar> getCalendars() {

        return calendarRepository.findAll();
    }

    @Override
    public Calendar getCalendarById(UUID id) throws IllegalArgumentException, NoSuchElementException {

        if (id == null) {
            throw new IllegalArgumentException("Can't get a calendar with null id");
        }
        Optional<Calendar> calendar = calendarRepository.findById(id);
        if (calendar.isEmpty()) {
            throw new NoSuchElementException(String.format("Calendar with id [%s] not found", id));
        }
        return calendar.get();
    }

    @Override
    public Calendar getCalendarByName(String name) throws IllegalArgumentException, NoSuchElementException {

        if (name == null) {
            throw new IllegalArgumentException("Can't get a calendar with null name");
        }
        Optional<Calendar> calendar = calendarRepository.findByName(name);
        if (calendar.isEmpty()) {
            throw new NoSuchElementException(String.format("Calendar with name [%s] not found", name));
        }
        return calendar.get();
    }
}
