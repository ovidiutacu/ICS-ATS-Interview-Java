package com.thales.interview.application.service;

import com.thales.interview.application.EventDtoMother;
import com.thales.interview.application.dto.CreateEventsRequestDto;
import com.thales.interview.application.dto.EventResponseDto;
import com.thales.interview.application.dto.UpdateEventDateRequestDto;
import com.thales.interview.application.dto.UpdateEventNameRequestDto;
import com.thales.interview.domain.EventMother;
import com.thales.interview.domain.model.CreateEventsRequest;
import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.model.UpdateEventDateRequest;
import com.thales.interview.domain.model.UpdateEventNameRequest;
import com.thales.interview.domain.service.CreateEventService;
import com.thales.interview.domain.service.DeleteEventService;
import com.thales.interview.domain.service.ReadEventService;
import com.thales.interview.domain.service.UpdateEventService;
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
class EventFacadeUnitTest {

    @Mock
    CreateEventService createEventService;
    @Mock
    ReadEventService readEventService;
    @Mock
    UpdateEventService updateEventService;
    @Mock
    DeleteEventService deleteEventService;
    @InjectMocks
    EventFacade underTest;

    @Test
    void whenGetEvents_thenDomainServiceGetEventsIsCalled() {

        //when
        underTest.getEvents();
        //then
        verify(readEventService, times(1)).getEvents();
    }

    @Test
    void givenNoEventsExisting_whenGetEvents_thenEmptyListIsReturned() {

        //given
        doReturn(new ArrayList<>()).when(readEventService).getEvents();
        //when
        List<EventResponseDto> events = underTest.getEvents();
        //then
        assertTrue(events.isEmpty());
    }

    @Test
    void givenEventsExisting_whenGetEvents_thenListOfEventsIsReturned() {

        //given
        Event event = EventMother.complete();
        doReturn(List.of(event)).when(readEventService).getEvents();
        //when
        List<EventResponseDto> events = underTest.getEvents();
        //then
        assertEquals(1, events.size());
        assertEquals(event.id(), events.get(0).id());
        assertEquals(event.type(), events.get(0).type());
        assertEquals(event.name(), events.get(0).name());
        assertEquals(event.description(), events.get(0).description());
        assertEquals(event.date(), events.get(0).date());
        assertEquals(event.calendarId(), events.get(0).calendarId());
    }

    @Test
    void givenRandomId_whenGetEventById_thenDomainServiceGetEventByIdIsCalled() {

        //given
        UUID eventId = UUID.randomUUID();
        //when
        underTest.getEventById(eventId);
        //then
        verify(readEventService, times(1)).getEventById(eventId);
    }

    @Test
    void givenIdNotExisting_whenGetEventById_thenExceptionIsThrown() {

        //given
        UUID id = UUID.randomUUID();
        doThrow(NoSuchElementException.class).when(readEventService).getEventById(id);
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.getEventById(id));
    }

    @Test
    void givenIdExisting_whenGetEventById_thenEventIsReturned() {

        //given
        Event event = EventMother.complete();
        UUID id = event.id();
        doReturn(event).when(readEventService).getEventById(id);
        //when
        EventResponseDto returnedEvent = underTest.getEventById(id);
        //then
        assertEquals(event.id(), returnedEvent.id());
        assertEquals(event.type(), returnedEvent.type());
        assertEquals(event.name(), returnedEvent.name());
        assertEquals(event.description(), returnedEvent.description());
        assertEquals(event.date(), returnedEvent.date());
        assertEquals(event.calendarId(), returnedEvent.calendarId());
    }

    @Test
    void whenDeleteEvents_thenDomainServiceDeleteEventsIsCalled() {

        //when
        underTest.deleteEvents();
        //then
        verify(deleteEventService, times(1)).deleteAllEvents();
    }

    @Test
    void givenRandomId_whenDeleteEventById_thenDomainServiceDeleteEventByIdIsCalled() {

        //given
        UUID id = UUID.randomUUID();
        //when
        underTest.deleteEventById(id);
        //then
        verify(deleteEventService, times(1)).deleteEventById(id);
    }

    @Test
    void givenCreateEventsRequest_whenCreateEvents_thenDomainServiceCreateEventsIsCalled() {

        //given
        CreateEventsRequestDto request = EventDtoMother.completeCreateEventsRequestDto();
        //when
        underTest.createEvents(request);
        //then
        verify(createEventService, times(1)).createEvents(any(CreateEventsRequest.class));
    }

    @Test
    void givenUpdateEventDateRequest_whenUpdateEventDate_thenDomainServiceUpdateEventDateIsCalled() {

        //given
        UpdateEventDateRequestDto request = EventDtoMother.completeUpdateDateRequestDto();
        //when
        underTest.updateEventDate(request);
        //then
        verify(updateEventService, times(1)).updateEventDate(any(UpdateEventDateRequest.class));
    }

    @Test
    void givenUpdateEventNameRequest_whenUpdateEventName_thenDomainServiceUpdateEventNameIsCalled() {

        //given
        UpdateEventNameRequestDto request = EventDtoMother.completeUpdateNameRequestDto();
        //when
        underTest.updateEventName(request);
        //then
        verify(updateEventService, times(1)).updateEventName(any(UpdateEventNameRequest.class));
    }
}
