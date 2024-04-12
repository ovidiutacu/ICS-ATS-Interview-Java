package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.exception.ElementAlreadyExistingException;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.repository.CalendarRepository;
import com.thales.interview.domain.service.CreateCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateCalendarServiceImpl implements CreateCalendarService {

    private final CalendarRepository calendarRepository;

    @Override
    public Calendar createCalendar(Calendar calendar) throws IllegalArgumentException, ElementAlreadyExistingException {

        validateCalendar(calendar);
        verifyIfCalendarWithSameNameAlreadyExists(calendar);
        return calendarRepository.save(calendar);
    }

    private void validateCalendar(Calendar calendar) {
        if (calendar == null) {
            throw new IllegalArgumentException("Can't create a null calendar");
        }
        if (calendar.name() == null || calendar.name().isBlank()) {
            throw new IllegalArgumentException("Can't create a calendar with null or blank name");
        }
    }

    private void verifyIfCalendarWithSameNameAlreadyExists(Calendar calendar) {
        Optional<Calendar> calendarOptional = calendarRepository.findByName(calendar.name());
        if (calendarOptional.isPresent()) {
            throw new ElementAlreadyExistingException(String.format("Calendar with the name [%s] already exists", calendar.name()));
        }
    }
}
