package com.thales.interview.domain;

import com.thales.interview.domain.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.thales.interview.domain.model.EventType.DAILY;

public class EventMother {

    public static Event complete() {

        return Event.builder()
                .id(UUID.randomUUID())
                .type(EventType.DAILY)
                .name("Complete Event")
                .description("This is a complete event")
                .date(LocalDate.now())
                .calendarId(UUID.randomUUID())
                .build();
    }

    public static UpdateEventDateRequest completeUpdateDateRequestForEvent(UUID eventId) {

        return UpdateEventDateRequest.builder()
                .eventId(eventId)
                .newDate(LocalDate.now().plusDays(100))
                .build();
    }

    public static UpdateEventNameRequest completeUpdateNameRequestForEvent(UUID eventId) {

        return UpdateEventNameRequest.builder()
                .eventId(eventId)
                .newName("New Name")
                .build();
    }

    public static CreateEventsRequest completeCreateEventsRequestForCalendar(UUID calendarId) {

        return CreateEventsRequest.builder()
                .name("Complete Event")
                .type(DAILY)
                .description("This is a complete description")
                .dates(List.of(LocalDate.now().plusDays(1), LocalDate.now().plusDays(2)))
                .calendarId(calendarId)
                .build();
    }
}
