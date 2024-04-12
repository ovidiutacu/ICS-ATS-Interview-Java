package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.model.CreateEventsRequest;
import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.model.EventType;
import com.thales.interview.domain.repository.EventRepository;
import com.thales.interview.domain.service.CreateEventService;
import com.thales.interview.domain.service.ReadCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateEventServiceImpl implements CreateEventService {

    private final EventRepository eventRepository;
    private final ReadCalendarService readCalendarService;

    @Override
    public List<Event> createEvents(CreateEventsRequest createEventsRequest) throws IllegalArgumentException {

        validateCreateEventsRequest(createEventsRequest);
        List<Event> eventsToBeCreated = convertCreateEventsRequestToEvents(createEventsRequest);
        return eventsToBeCreated.stream().map(eventRepository::save).toList();
    }

    private void validateCreateEventsRequest(CreateEventsRequest createEventsRequest) {
        if (createEventsRequest == null) {
            throw new IllegalArgumentException("Can't create events with null request");
        }
        validateName(createEventsRequest.name());
        validateType(createEventsRequest.type());
        validateDates(createEventsRequest.dates());
        validateCalendar(createEventsRequest.calendarId());
    }

    private void validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Event name cannot be null");
        }
    }

    private void validateType(EventType type) {
        if (type == null) {
            throw new IllegalArgumentException("Event type cannot be null");
        }
    }

    private void validateDates(List<LocalDate> dates) {
        if (dates == null || dates.isEmpty()) {
            throw new IllegalArgumentException("Event dates cannot be null or empty");
        }
        verifyDatesToBeInTheFuture(dates);
    }

    private void verifyDatesToBeInTheFuture(List<LocalDate> dates) {
        LocalDate currentDate = LocalDate.now();
        boolean allDatesAreInTheFuture = dates.stream()
                .allMatch(date -> date.isAfter(currentDate));
        if (!allDatesAreInTheFuture) {
            throw new IllegalArgumentException("All event dates must be in the future");
        }
    }

    private void validateCalendar(UUID calendarId) {
        if (calendarId == null) {
            throw new IllegalArgumentException("Calendar id cannot be null");
        }
        readCalendarService.getCalendarById(calendarId);
    }

    private List<Event> convertCreateEventsRequestToEvents(CreateEventsRequest createEventsRequest) {
        return createEventsRequest.dates().stream()
                .map(date -> Event.builder()
                        .type(createEventsRequest.type())
                        .name(createEventsRequest.name())
                        .description(createEventsRequest.description())
                        .date(date)
                        .calendarId(createEventsRequest.calendarId())
                        .build())
                .toList();
    }
}
