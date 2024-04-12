package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.CalendarMother;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.repository.CalendarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReadCalendarServiceImplUnitTest {

    @Mock
    CalendarRepository calendarRepository;
    @InjectMocks
    ReadCalendarServiceImpl underTest;

    @Test
    void whenGetCalendars_thenRepositoryFindAllIsCalled() {

        //when
        underTest.getCalendars();
        //then
        verify(calendarRepository, times(1)).findAll();
    }

    @Test
    void givenNoCalendarsInRepository_whenGetCalendars_thenReturnEmptyList() {

        //given
        doReturn(new ArrayList<>()).when(calendarRepository).findAll();
        //when
        List<Calendar> calendars = underTest.getCalendars();
        //then
        assertTrue(calendars.isEmpty());
    }

    @Test
    void givenCalendarsExisting_whenGetCalendars_thenListOfCalendarsIsReturned() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(List.of(calendar)).when(calendarRepository).findAll();
        //when
        List<Calendar> calendars = underTest.getCalendars();
        //then
        assertEquals(1, calendars.size());
        assertEquals(calendar, calendars.get(0));
    }

    @Test
    void givenNullId_whenGetCalendarById_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.getCalendarById(null));
    }

    @Test
    void givenRandomId_whenGetCalendarById_thenRepositoryFindByIdIsCalled() {

        //given
        UUID id = UUID.randomUUID();
        doReturn(Optional.of(CalendarMother.complete())).when(calendarRepository).findById(any());
        //when
        underTest.getCalendarById(id);
        //then
        verify(calendarRepository, times(1)).findById(id);
    }

    @Test
    void givenNotExistingId_whenGetCalendarById_thenExceptionIsThrown() {

        //given
        UUID id = UUID.randomUUID();
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.getCalendarById(id));
    }

    @Test
    void givenExistingId_whenGetCalendarById_thenCalendarIsReturned() {

        //given
        Calendar calendar = CalendarMother.complete();
        UUID id = calendar.id();
        doReturn(Optional.of(calendar)).when(calendarRepository).findById(id);
        //when
        Calendar returnedCalendar = underTest.getCalendarById(id);
        //then
        assertEquals(calendar, returnedCalendar);
    }

    @Test
    void givenNullName_whenGetCalendarByName_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.getCalendarByName(null));
    }

    @Test
    void givenRandomName_whenGetCalendarByName_thenRepositoryFindByNameIsCalled() {

        //given
        String name = UUID.randomUUID().toString();
        doReturn(Optional.of(CalendarMother.complete())).when(calendarRepository).findByName(any());
        //when
        underTest.getCalendarByName(name);
        //then
        verify(calendarRepository, times(1)).findByName(name);
    }

    @Test
    void givenNotExistingName_whenGetCalendarByName_thenExceptionIsThrown() {

        //given
        String name = UUID.randomUUID().toString();
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.getCalendarByName(name));
    }

    @Test
    void givenExistingName_whenGetCalendarByName_thenCalendarIsReturned() {

        //given
        Calendar calendar = CalendarMother.complete();
        String name = calendar.name();
        doReturn(Optional.of(calendar)).when(calendarRepository).findByName(name);
        //when
        Calendar returnedCalendar = underTest.getCalendarByName(name);
        //then
        assertEquals(calendar, returnedCalendar);
    }
}
