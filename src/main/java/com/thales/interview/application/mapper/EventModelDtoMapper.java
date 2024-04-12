package com.thales.interview.application.mapper;

import com.thales.interview.application.dto.CreateEventsRequestDto;
import com.thales.interview.application.dto.EventResponseDto;
import com.thales.interview.application.dto.UpdateEventDateRequestDto;
import com.thales.interview.application.dto.UpdateEventNameRequestDto;
import com.thales.interview.domain.model.CreateEventsRequest;
import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.model.UpdateEventDateRequest;
import com.thales.interview.domain.model.UpdateEventNameRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventModelDtoMapper {

    public static CreateEventsRequest toModel(CreateEventsRequestDto dto) {

        if (dto == null) {
            return null;
        }

        return CreateEventsRequest.builder()
                .type(dto.type())
                .name(dto.name())
                .description(dto.description())
                .calendarId(dto.calendarId())
                .dates(extractDates(dto.startDate(), dto.endDate()))
                .build();
    }

    private static List<LocalDate> extractDates(LocalDate startDate, LocalDate endDate) {
        validateInput(startDate, endDate);
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }
        return dates;
    }

    private static void validateInput(LocalDate startDate, LocalDate endDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    public static EventResponseDto toDto(Event event) {

        if (event == null) {
            return null;
        }

        return EventResponseDto.builder()
                .id(event.id())
                .type(event.type())
                .name(event.name())
                .description(event.description())
                .date(event.date())
                .calendarId(event.calendarId())
                .build();
    }

    public static UpdateEventDateRequest toUpdateEventDateRequestModel(UpdateEventDateRequestDto dto) {

        if (dto == null) {
            return null;
        }

        return UpdateEventDateRequest.builder()
                .eventId(dto.eventId())
                .newDate(dto.newDate())
                .build();
    }

    public static UpdateEventNameRequest toUpdateEventNameRequestModel(UpdateEventNameRequestDto dto) {

        if (dto == null) {
            return null;
        }

        return UpdateEventNameRequest.builder()
                .eventId(dto.eventId())
                .newName(dto.newName())
                .build();
    }
}
