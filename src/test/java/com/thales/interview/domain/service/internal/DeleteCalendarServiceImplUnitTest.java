package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.CalendarMother;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.repository.CalendarRepository;
import com.thales.interview.domain.service.DeleteEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DeleteCalendarServiceImplUnitTest {

    @Mock
    private CalendarRepository calendarRepository;
    @Mock
    DeleteEventService deleteEventService;
    @InjectMocks
    DeleteCalendarServiceImpl underTest;

    @Test
    void whenDeleteAllCalendars_thenRepositoryDeleteAllIdCalled() {

        //when
        underTest.deleteAllCalendars();
        //then
        verify(calendarRepository, times(1)).deleteAll();
    }

    @Test
    void whenDeleteAllCalendars_thenDeleteAllEventsIsCalled() {

        //when
        underTest.deleteAllCalendars();
        //then
        verify(deleteEventService, times(1)).deleteAllEvents();
    }

    @Test
    void givenNullId_whenDeleteCalendarById_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.deleteCalendarById(null));
    }

    @Test
    void givenNotExistingId_whenDeleteCalendarById_thenNothingHappens() {

        //given
        UUID id = UUID.randomUUID();
        doReturn(Optional.empty()).when(calendarRepository).findById(id);
        //when
        assertDoesNotThrow(() -> underTest.deleteCalendarById(id));
        //then
        verify(calendarRepository, never()).delete(any());
        verify(deleteEventService, never()).deleteEventsByCalendarId(any());
    }

    @Test
    void givenExistingId_whenDeleteCalendarById_thenRepositoryDeleteIsCalledAndAssociatedEventsAreDeleted() {

        //given
        Calendar calendar = CalendarMother.complete();
        UUID id = calendar.id();
        doReturn(Optional.of(calendar)).when(calendarRepository).findById(id);
        //when
        underTest.deleteCalendarById(id);
        //then
        verify(calendarRepository, times(1)).delete(calendar);
        verify(deleteEventService, times(1)).deleteEventsByCalendarId(id);
    }

    @Test
    void givenNullName_whenDeleteCalendarByName_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.deleteCalendarByName(null));
    }

    @Test
    void givenNotExistingName_whenDeleteCalendarByName_thenNothingHappens() {

        //given
        String name = UUID.randomUUID().toString();
        doReturn(Optional.empty()).when(calendarRepository).findByName(name);
        //when
        assertDoesNotThrow(() -> underTest.deleteCalendarByName(name));
        //then
        verify(calendarRepository, never()).delete(any());
        verify(deleteEventService, never()).deleteEventsByCalendarId(any());
    }

    @Test
    void givenExistingName_whenDeleteCalendarByName_thenRepositoryDeleteIsCalledAndAssociatedEventsAreDeleted() {

        //given
        Calendar calendar = CalendarMother.complete();
        String name = calendar.name();
        doReturn(Optional.of(calendar)).when(calendarRepository).findByName(name);
        //when
        underTest.deleteCalendarByName(name);
        //then
        verify(calendarRepository, times(1)).delete(calendar);
        verify(deleteEventService, times(1)).deleteEventsByCalendarId(calendar.id());
    }
}
