package com.thales.interview.application;

import com.thales.interview.application.dto.CreateEventsRequestDto;
import com.thales.interview.application.dto.EventResponseDto;
import com.thales.interview.application.dto.UpdateEventDateRequestDto;
import com.thales.interview.application.dto.UpdateEventNameRequestDto;

import java.time.LocalDate;
import java.util.UUID;

import static com.thales.interview.domain.model.EventType.DAILY;

public class EventDtoMother {

    public static EventResponseDto complete() {

        return EventResponseDto.builder()
                .id(UUID.randomUUID())
                .type(DAILY)
                .name("Random Name")
                .description("Random Description")
                .date(LocalDate.now().plusDays(1))
                .calendarId(UUID.randomUUID())
                .build();
    }

    public static CreateEventsRequestDto completeCreateEventsRequestDto() {

        return CreateEventsRequestDto.builder()
                .type(DAILY)
                .name("Random Name")
                .description("Random Description")
                .startDate(LocalDate.now().plusDays(10))
                .endDate(LocalDate.now().plusDays(20))
                .calendarId(UUID.randomUUID())
                .build();
    }

    public static CreateEventsRequestDto completeCreateEventsRequestDtoForCalendar(UUID calendarId) {

        return completeCreateEventsRequestDto().toBuilder().calendarId(calendarId).build();
    }

    public static UpdateEventDateRequestDto completeUpdateDateRequestDto() {

        return completeUpdateDateRequestDtoForEvent(UUID.randomUUID());
    }

    public static UpdateEventDateRequestDto completeUpdateDateRequestDtoForEvent(UUID eventId) {

        return UpdateEventDateRequestDto.builder()
                .eventId(eventId)
                .newDate(LocalDate.now().plusDays(100))
                .build();
    }

    public static UpdateEventNameRequestDto completeUpdateNameRequestDto() {

        return completeUpdateNameRequestDtoForEvent(UUID.randomUUID());
    }

    public static UpdateEventNameRequestDto completeUpdateNameRequestDtoForEvent(UUID eventId) {

        return UpdateEventNameRequestDto.builder()
                .eventId(eventId)
                .newName("New Name")
                .build();
    }
}
