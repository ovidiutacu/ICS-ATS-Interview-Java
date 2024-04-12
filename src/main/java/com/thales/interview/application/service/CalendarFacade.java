package com.thales.interview.application.service;

import com.thales.interview.application.dto.CalendarResponseDto;
import com.thales.interview.application.dto.CreateCalendarRequestDto;
import com.thales.interview.application.dto.EventResponseDto;
import com.thales.interview.application.mapper.CalendarModelDtoMapper;
import com.thales.interview.application.mapper.EventModelDtoMapper;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.service.CreateCalendarService;
import com.thales.interview.domain.service.DeleteCalendarService;
import com.thales.interview.domain.service.ReadCalendarService;
import com.thales.interview.domain.service.ReadEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CalendarFacade {

    private final CreateCalendarService createCalendarService;
    private final ReadCalendarService readCalendarService;
    private final DeleteCalendarService deleteCalendarService;
    private final ReadEventService readEventService;

    public List<CalendarResponseDto> getCalendars() {

        return readCalendarService.getCalendars().stream()
                .map(CalendarModelDtoMapper::toDto)
                .map(this::addEventsToCalendar)
                .toList();
    }

    private CalendarResponseDto addEventsToCalendar(CalendarResponseDto calendar) {
        List<EventResponseDto> events = getEventsAssociatedWithCalendar(calendar);
        return calendar.toBuilder()
                .events(events)
                .build();
    }

    private List<EventResponseDto> getEventsAssociatedWithCalendar(CalendarResponseDto calendar) {
        UUID id = calendar.id();
        return readEventService.getEventsByCalendarId(id).stream()
                .map(EventModelDtoMapper::toDto)
                .toList();
    }

    public CalendarResponseDto getCalendarById(UUID id) {

        return addEventsToCalendar(CalendarModelDtoMapper.toDto(readCalendarService.getCalendarById(id)));
    }

    public CalendarResponseDto getCalendarByName(String name) {

        return addEventsToCalendar(CalendarModelDtoMapper.toDto(readCalendarService.getCalendarByName(name)));
    }

    public void deleteCalendars() {

        deleteCalendarService.deleteAllCalendars();
    }

    public void deleteCalendarById(UUID id) {

        deleteCalendarService.deleteCalendarById(id);
    }

    public void deleteCalendarByName(String name) {

        deleteCalendarService.deleteCalendarByName(name);
    }

    public CalendarResponseDto createCalendar(CreateCalendarRequestDto createCalendarRequestDto) {

        Calendar toCreate = CalendarModelDtoMapper.toModel(createCalendarRequestDto);
        Calendar createdCalendar = createCalendarService.createCalendar(toCreate);
        return CalendarModelDtoMapper.toDto(createdCalendar);
    }
}
