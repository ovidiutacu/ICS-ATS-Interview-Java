package com.thales.interview.application.controller;

import com.thales.interview.application.CalendarDtoMother;
import com.thales.interview.application.dto.CalendarResponseDto;
import com.thales.interview.application.dto.CreateCalendarRequestDto;
import com.thales.interview.application.service.CalendarFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CalendarControllerUnitTest {

    @Mock
    CalendarFacade calendarFacade;
    @InjectMocks
    CalendarController underTest;

    @Test
    void whenGetCalendars_thenFacadeGetCalendarsIsCalled() {

        //when
        underTest.getCalendars();
        //then
        verify(calendarFacade, times(1)).getCalendars();
    }

    @Test
    void givenNoCalendars_whenGetCalendars_thenEmptyListIsReturned() {

        //given
        doReturn(new ArrayList<>()).when(calendarFacade).getCalendars();
        //when
        ResponseEntity<List<CalendarResponseDto>> calendars = underTest.getCalendars();
        //then
        assertNotNull(calendars.getBody());
        assertTrue(calendars.getBody().isEmpty());
    }

    @Test
    void givenCalendarsExisting_whenGetCalendars_thenListOfCalendarsIsReturned() {

        //given
        CalendarResponseDto calendar = CalendarDtoMother.complete();
        doReturn(List.of(calendar)).when(calendarFacade).getCalendars();
        //when
        ResponseEntity<List<CalendarResponseDto>> calendars = underTest.getCalendars();
        //then
        assertNotNull(calendars.getBody());
        assertEquals(1, calendars.getBody().size());
        assertEquals(calendar, calendars.getBody().get(0));
    }

    @Test
    void givenRandomId_whenGetCalendarById_thenFacadeGetCalendarByIdIsCalled() {

        //given
        UUID id = UUID.randomUUID();
        //when
        underTest.getCalendarById(id);
        //then
        verify(calendarFacade, times(1)).getCalendarById(id);
    }

    @Test
    void givenExistingId_whenGetCalendarById_thenCalendarIsReturned() {

        //given
        CalendarResponseDto calendar = CalendarDtoMother.complete();
        doReturn(calendar).when(calendarFacade).getCalendarById(calendar.id());
        //when
        ResponseEntity<CalendarResponseDto> returnedCalendar = underTest.getCalendarById(calendar.id());
        //then
        assertNotNull(returnedCalendar.getBody());
    }

    @Test
    void givenRandomName_whenGetCalendarByName_thenFacadeGetCalendarByNameIsCalled() {

        //given
        String name = UUID.randomUUID().toString();
        //when
        underTest.getCalendarByName(name);
        //then
        verify(calendarFacade, times(1)).getCalendarByName(name);
    }

    @Test
    void givenExistingName_whenGetCalendarByName_thenCalendarIsReturned() {

        //given
        CalendarResponseDto calendar = CalendarDtoMother.complete();
        doReturn(calendar).when(calendarFacade).getCalendarByName(calendar.name());
        //when
        ResponseEntity<CalendarResponseDto> returnedCalendar = underTest.getCalendarByName(calendar.name());
        //then
        assertNotNull(returnedCalendar.getBody());
    }

    @Test
    void whenDeleteCalendars_thenFacadeDeleteCalendarsIsCalled() {

        //when
        underTest.deleteCalendars();
        //then
        verify(calendarFacade, times(1)).deleteCalendars();
    }

    @Test
    void givenRandomId_whenDeleteCalendarById_thenFacadeDeleteCalendarByIdIsCalled() {

        //given
        UUID id = UUID.randomUUID();
        //when
        underTest.deleteCalendarById(id);
        //then
        verify(calendarFacade, times(1)).deleteCalendarById(id);
    }

    @Test
    void givenRandomName_whenDeleteCalendarByName_thenServiceDeleteCalendarByNameIsCalled() {

        //given
        String name = UUID.randomUUID().toString();
        //when
        underTest.deleteCalendarByName(name);
        //then
        verify(calendarFacade, times(1)).deleteCalendarByName(name);
    }

    @Test
    void givenCreateCalendarRequest_whenCreateCalendar_thenFacadeCreateCalendarIsCalled() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        //when
        underTest.createCalendar(createCalendarRequest);
        //then
        verify(calendarFacade, times(1)).createCalendar(any());
    }

    @Test
    void givenCreateCalendarRequest_whenCreateCalendar_thenCreatedCalendarIsReturned() {

        //given
        CreateCalendarRequestDto createCalendarRequest = CalendarDtoMother.completeCreateCalendarRequest();
        when(calendarFacade.createCalendar(any())).thenAnswer((Answer<CalendarResponseDto>) invocation -> {
            CreateCalendarRequestDto argument = (CreateCalendarRequestDto) invocation.getArguments()[0];
            return new CalendarResponseDto(
                    UUID.randomUUID(),
                    argument.name(),
                    argument.description(),
                    new ArrayList<>()
            );
        });
        //when
        ResponseEntity<CalendarResponseDto> created = underTest.createCalendar(createCalendarRequest);
        //then
        assertNotNull(created.getBody());
        assertNotNull(created.getBody().id());
        assertEquals(createCalendarRequest.name(), created.getBody().name());
        assertEquals(createCalendarRequest.description(), created.getBody().description());
        assertTrue(created.getBody().events().isEmpty());
    }
}
