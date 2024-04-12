package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.EventMother;
import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.repository.EventRepository;
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
class ReadEventServiceImplUnitTest {

    @Mock
    EventRepository eventRepository;
    @InjectMocks
    ReadEventServiceImpl underTest;

    @Test
    void whenGetEvents_thenRepositoryFindAllIsCalled() {

        //when
        underTest.getEvents();
        //then
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void givenNoEventsInRepository_whenGetEvents_thenReturnEmptyList() {

        //given
        doReturn(new ArrayList<>()).when(eventRepository).findAll();
        //when
        List<Event> events = underTest.getEvents();
        //then
        assertTrue(events.isEmpty());
    }

    @Test
    void givenEventsExisting_whenGetEvents_thenListOfEventsIsReturned() {

        //given
        Event event = EventMother.complete();
        doReturn(List.of(event)).when(eventRepository).findAll();
        //when
        List<Event> events = underTest.getEvents();
        //then
        assertEquals(1, events.size());
        assertEquals(event, events.get(0));
    }

    @Test
    void givenNullId_whenGetEventById_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.getEventById(null));
    }

    @Test
    void givenRandomId_whenGetEventById_thenRepositoryFindByIdIsCalled() {

        //given
        UUID id = UUID.randomUUID();
        doReturn(Optional.of(EventMother.complete())).when(eventRepository).findById(any());
        //when
        underTest.getEventById(id);
        //then
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void givenNotExistingId_whenGetEventById_thenExceptionIsThrown() {

        //given
        UUID id = UUID.randomUUID();
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.getEventById(id));
    }

    @Test
    void givenExistingId_whenGetEventById_thenEventIsReturned() {

        //given
        Event event = EventMother.complete();
        UUID id = event.id();
        doReturn(Optional.of(event)).when(eventRepository).findById(id);
        //when
        Event returnedEvent = underTest.getEventById(id);
        //then
        assertEquals(event, returnedEvent);
    }

    @Test
    void givenNullCalendarId_whenGetEventsByCalendarId_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.getEventsByCalendarId(null));
    }

    @Test
    void givenRandomCalendarId_whenGetEventsByCalendarId_thenRepositoryFindByCalendarIdIsCalled() {

        //given
        UUID calendarId = UUID.randomUUID();
        doReturn(new ArrayList<>()).when(eventRepository).findByCalendarId(any());
        //when
        underTest.getEventsByCalendarId(calendarId);
        //then
        verify(eventRepository, times(1)).findByCalendarId(calendarId);
    }

    @Test
    void givenNotExistingCalendarId_whenGetEventsByCalendarId_thenNoEventIsReturned() {

        //given
        UUID calendarId = UUID.randomUUID();
        doReturn(new ArrayList<>()).when(eventRepository).findByCalendarId(calendarId);
        //when
        List<Event> events = underTest.getEventsByCalendarId(calendarId);
        //then
        assertTrue(events.isEmpty());
    }

    @Test
    void givenExistingCalendarId_whenGetEventsByCalendarId_thenEventsAreReturned() {

        //given
        Event event = EventMother.complete();
        UUID calendarId = event.calendarId();
        doReturn(List.of(event)).when(eventRepository).findByCalendarId(calendarId);
        //when
        List<Event> events = underTest.getEventsByCalendarId(calendarId);
        //then
        assertEquals(1, events.size());
        assertEquals(event, events.get(0));
    }
}
