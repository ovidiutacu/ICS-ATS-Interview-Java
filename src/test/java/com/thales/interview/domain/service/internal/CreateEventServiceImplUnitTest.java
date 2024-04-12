package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.CalendarMother;
import com.thales.interview.domain.EventMother;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.model.CreateEventsRequest;
import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.repository.EventRepository;
import com.thales.interview.domain.service.ReadCalendarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreateEventServiceImplUnitTest {

    @Mock
    EventRepository eventRepository;
    @Mock
    ReadCalendarService readCalendarService;
    @InjectMocks
    CreateEventServiceImpl underTest;

    @Test
    void givenNullRequest_whenCreateEvents_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createEvents(null));
    }

    @Test
    void givenRequestWithoutName_whenCreateEvents_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(calendar).when(readCalendarService).getCalendarById(calendar.id());
        CreateEventsRequest request = EventMother.completeCreateEventsRequestForCalendar(calendar.id()).toBuilder()
                .name(null)
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createEvents(request));
    }

    @Test
    void givenRequestWithoutType_whenCreateEvents_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(calendar).when(readCalendarService).getCalendarById(calendar.id());
        CreateEventsRequest request = EventMother.completeCreateEventsRequestForCalendar(calendar.id()).toBuilder()
                .type(null)
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createEvents(request));
    }

    @Test
    void givenRequestWithoutDates_whenCreateEvents_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(calendar).when(readCalendarService).getCalendarById(calendar.id());
        CreateEventsRequest request = EventMother.completeCreateEventsRequestForCalendar(calendar.id()).toBuilder()
                .dates(null)
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createEvents(request));
    }

    @Test
    void givenRequestWithCurrentDate_whenCreateEvents_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(calendar).when(readCalendarService).getCalendarById(calendar.id());
        CreateEventsRequest request = EventMother.completeCreateEventsRequestForCalendar(calendar.id()).toBuilder()
                .dates(List.of(LocalDate.now()))
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createEvents(request));
    }

    @Test
    void givenRequestWithDateInThePast_whenCreateEvents_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(calendar).when(readCalendarService).getCalendarById(calendar.id());
        CreateEventsRequest request = EventMother.completeCreateEventsRequestForCalendar(calendar.id()).toBuilder()
                .dates(List.of(LocalDate.now().minusDays(1)))
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createEvents(request));
    }

    @Test
    void givenRequestWithoutCalendar_whenCreateEvents_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(calendar).when(readCalendarService).getCalendarById(calendar.id());
        CreateEventsRequest request = EventMother.completeCreateEventsRequestForCalendar(calendar.id()).toBuilder()
                .calendarId(null)
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createEvents(request));
    }

    @Test
    void givenRequestWithoutExistingCalendar_whenCreateEvents_thenExceptionIsThrown() {

        //given
        doThrow(NoSuchElementException.class).when(readCalendarService).getCalendarById(any());
        CreateEventsRequest request = EventMother.completeCreateEventsRequestForCalendar(UUID.randomUUID());
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.createEvents(request));
    }

    @Test
    void givenValidRequest_whenCreateEvents_thenEventsAreCreated() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(calendar).when(readCalendarService).getCalendarById(calendar.id());
        CreateEventsRequest request = EventMother.completeCreateEventsRequestForCalendar(calendar.id());
        when(eventRepository.save(any())).thenAnswer((Answer<Event>) invocation -> {
            Event argument = (Event) invocation.getArguments()[0];
            return argument.toBuilder()
                    .id(UUID.randomUUID())
                    .build();
        });
        //when
        List<Event> createdEvents = underTest.createEvents(request);
        //then
        assertEquals(request.dates().size(), createdEvents.size());
        for (Event createdEvent : createdEvents) {
            assertNotNull(createdEvent.id());
            assertEquals(request.name(), createdEvent.name());
            assertEquals(request.description(), createdEvent.description());
            assertTrue(request.dates().contains(createdEvent.date()));
            assertEquals(request.calendarId(), createdEvent.calendarId());
        }
    }
}
