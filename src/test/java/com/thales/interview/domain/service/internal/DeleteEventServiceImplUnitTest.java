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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DeleteEventServiceImplUnitTest {

    @Mock
    EventRepository eventRepository;
    @InjectMocks
    DeleteEventServiceImpl underTest;

    @Test
    void whenDeleteAllEvents_thenRepositoryDeleteAllIsCalled() {

        //when
        underTest.deleteAllEvents();
        //then
        verify(eventRepository, times(1)).deleteAll();
    }

    @Test
    void givenNullId_whenDeleteEventById_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.deleteEventById(null));
    }

    @Test
    void givenNotExistingId_whenDeleteEventById_thenNothingHappens() {

        //given
        UUID id = UUID.randomUUID();
        doReturn(Optional.empty()).when(eventRepository).findById(id);
        //when
        assertDoesNotThrow(() -> underTest.deleteEventById(id));
        //then
        verify(eventRepository, never()).delete(any());
    }

    @Test
    void givenExistingId_whenDeleteEventById_thenRepositoryDeleteIsCalled() {

        //given
        Event event = EventMother.complete();
        UUID id = event.id();
        doReturn(Optional.of(event)).when(eventRepository).findById(id);
        //when
        underTest.deleteEventById(id);
        //then
        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    void givenNullCalendarId_whenDeleteEventsByCalendarId_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.deleteEventsByCalendarId(null));
    }

    @Test
    void givenNotExistingCalendarId_whenDeleteEventsByCalendarId_thenNothingHappens() {

        //given
        UUID calendarId = UUID.randomUUID();
        doReturn(new ArrayList<>()).when(eventRepository).findByCalendarId(calendarId);
        //when
        assertDoesNotThrow(() -> underTest.deleteEventsByCalendarId(calendarId));
        //then
        verify(eventRepository, never()).delete(any());
    }

    @Test
    void givenExistingCalendarId_whenDeleteEventsByCalendarId_thenRepositoryDeleteIsCalled() {

        //given
        Event event = EventMother.complete();
        UUID calendarId = event.calendarId();
        doReturn(List.of(event)).when(eventRepository).findByCalendarId(calendarId);
        //when
        underTest.deleteEventsByCalendarId(calendarId);
        //then
        verify(eventRepository, times(1)).delete(any());
    }
}
