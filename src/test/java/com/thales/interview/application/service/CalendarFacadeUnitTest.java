package com.thales.interview.application.service;

import com.thales.interview.application.CalendarDtoMother;
import com.thales.interview.application.dto.CalendarResponseDto;
import com.thales.interview.application.dto.CreateCalendarRequestDto;
import com.thales.interview.domain.CalendarMother;
import com.thales.interview.domain.EventMother;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.service.CreateCalendarService;
import com.thales.interview.domain.service.DeleteCalendarService;
import com.thales.interview.domain.service.ReadCalendarService;
import com.thales.interview.domain.service.ReadEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CalendarFacadeUnitTest {

    @Mock
    CreateCalendarService createCalendarService;
    @Mock
    ReadCalendarService readCalendarService;
    @Mock
    DeleteCalendarService deleteCalendarService;
    @Mock
    ReadEventService readEventService;
    @InjectMocks
    CalendarFacade underTest;

    @Test
    void whenGetCalendars_thenDomainServiceGetCalendarsIsCalled() {

        //when
        underTest.getCalendars();
        //then
        verify(readCalendarService, times(1)).getCalendars();
    }

    @Test
    void givenNoCalendarsExisting_whenGetCalendars_thenEmptyListIsReturned() {

        //given
        doReturn(new ArrayList<>()).when(readCalendarService).getCalendars();
        //when
        List<CalendarResponseDto> calendars = underTest.getCalendars();
        //then
        assertTrue(calendars.isEmpty());
    }

    @Test
    void givenNoCalendarsExisting_whenGetCalendars_thenDomainServiceGetEventsByCalendarIdIsNotCalled() {

        //given
        doReturn(new ArrayList<>()).when(readCalendarService).getCalendars();
        //when
        underTest.getCalendars();
        //then
        verify(readEventService, never()).getEventsByCalendarId(any());
    }

    @Test
    void givenCalendarsExisting_whenGetCalendars_thenDomainServiceGetEventsByCalendarIdIsCalled() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(List.of(calendar)).when(readCalendarService).getCalendars();
        //when
        underTest.getCalendars();
        //then
        verify(readEventService, times(1)).getEventsByCalendarId(calendar.id());
    }

    @Test
    void givenCalendarsExisting_whenGetCalendars_thenListOfCalendarsWithEventsIsReturned() {

        //given
        Calendar calendar = CalendarMother.complete();
        Event event = EventMother.complete().toBuilder().calendarId(calendar.id()).build();
        doReturn(List.of(calendar)).when(readCalendarService).getCalendars();
        doReturn(List.of(event)).when(readEventService).getEventsByCalendarId(calendar.id());
        //when
        List<CalendarResponseDto> calendars = underTest.getCalendars();
        //then
        assertEquals(1, calendars.size());
        assertEquals(calendar.id(), calendars.get(0).id());
        assertEquals(calendar.name(), calendars.get(0).name());
        assertEquals(calendar.description(), calendars.get(0).description());
        assertEquals(1, calendars.get(0).events().size());
        assertEquals(event.id(), calendars.get(0).events().get(0).id());
        assertEquals(event.type(), calendars.get(0).events().get(0).type());
        assertEquals(event.name(), calendars.get(0).events().get(0).name());
        assertEquals(event.description(), calendars.get(0).events().get(0).description());
        assertEquals(event.date(), calendars.get(0).events().get(0).date());
        assertEquals(event.calendarId(), calendars.get(0).events().get(0).calendarId());
    }

    @Test
    void givenIdNotExisting_whenGetCalendarById_thenExceptionIsThrown() {

        //given
        UUID id = UUID.randomUUID();
        doThrow(NoSuchElementException.class).when(readCalendarService).getCalendarById(id);
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.getCalendarById(id));
    }

    @Test
    void givenIdExisting_whenGetCalendarById_thenCalendarWithEventsIsReturned() {

        //given
        Calendar calendar = CalendarMother.complete();
        Event event = EventMother.complete().toBuilder().calendarId(calendar.id()).build();
        doReturn(calendar).when(readCalendarService).getCalendarById(calendar.id());
        doReturn(List.of(event)).when(readEventService).getEventsByCalendarId(calendar.id());
        //when
        CalendarResponseDto returnedCalendar = underTest.getCalendarById(calendar.id());
        //then
        assertEquals(calendar.id(), returnedCalendar.id());
        assertEquals(calendar.name(), returnedCalendar.name());
        assertEquals(calendar.description(), returnedCalendar.description());
        assertEquals(1, returnedCalendar.events().size());
        assertEquals(event.id(), returnedCalendar.events().get(0).id());
        assertEquals(event.type(), returnedCalendar.events().get(0).type());
        assertEquals(event.name(), returnedCalendar.events().get(0).name());
        assertEquals(event.description(), returnedCalendar.events().get(0).description());
        assertEquals(event.date(), returnedCalendar.events().get(0).date());
        assertEquals(event.calendarId(), returnedCalendar.events().get(0).calendarId());
    }

    @Test
    void givenNameNotExisting_whenGetCalendarByName_thenExceptionIsThrown() {

        //given
        String name = UUID.randomUUID().toString();
        doThrow(NoSuchElementException.class).when(readCalendarService).getCalendarByName(name);
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.getCalendarByName(name));
    }

    @Test
    void givenNameExisting_whenGetCalendarByName_thenCalendarWithEventsIsReturned() {

        //given
        Calendar calendar = CalendarMother.complete();
        Event event = EventMother.complete().toBuilder().calendarId(calendar.id()).build();
        doReturn(calendar).when(readCalendarService).getCalendarByName(calendar.name());
        doReturn(List.of(event)).when(readEventService).getEventsByCalendarId(calendar.id());
        //when
        CalendarResponseDto returnedCalendar = underTest.getCalendarByName(calendar.name());
        //then
        assertEquals(calendar.id(), returnedCalendar.id());
        assertEquals(calendar.name(), returnedCalendar.name());
        assertEquals(calendar.description(), returnedCalendar.description());
        assertEquals(1, returnedCalendar.events().size());
        assertEquals(event.id(), returnedCalendar.events().get(0).id());
        assertEquals(event.type(), returnedCalendar.events().get(0).type());
        assertEquals(event.name(), returnedCalendar.events().get(0).name());
        assertEquals(event.description(), returnedCalendar.events().get(0).description());
        assertEquals(event.date(), returnedCalendar.events().get(0).date());
        assertEquals(event.calendarId(), returnedCalendar.events().get(0).calendarId());
    }

    @Test
    void whenDeleteCalendars_thenDomainServiceDeleteCalendarsIsCalled() {

        //when
        underTest.deleteCalendars();
        //then
        verify(deleteCalendarService, times(1)).deleteAllCalendars();
    }

    @Test
    void givenRandomId_whenCalendarById_thenDomainServiceDeleteCalendarByIdIsCalled() {

        //given
        UUID id = UUID.randomUUID();
        //when
        underTest.deleteCalendarById(id);
        //then
        verify(deleteCalendarService, times(1)).deleteCalendarById(id);
    }

    @Test
    void givenRandomName_whenCalendarByName_thenDomainServiceDeleteCalendarByNameIsCalled() {

        //given
        String name = UUID.randomUUID().toString();
        //when
        underTest.deleteCalendarByName(name);
        //then
        verify(deleteCalendarService, times(1)).deleteCalendarByName(name);
    }

    @Test
    void givenCreateCalendarRequest_whenCreateCalendar_thenDomainServiceCreateCalendarIsCalled() {

        //given
        CreateCalendarRequestDto request = CalendarDtoMother.completeCreateCalendarRequest();
        //when
        underTest.createCalendar(request);
        //then
        verify(createCalendarService, times(1)).createCalendar(any(Calendar.class));
    }
}
