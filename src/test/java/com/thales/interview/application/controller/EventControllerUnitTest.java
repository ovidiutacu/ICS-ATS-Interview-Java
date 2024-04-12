package com.thales.interview.application.controller;

import com.thales.interview.application.EventDtoMother;
import com.thales.interview.application.dto.CreateEventsRequestDto;
import com.thales.interview.application.dto.EventResponseDto;
import com.thales.interview.application.dto.UpdateEventDateRequestDto;
import com.thales.interview.application.dto.UpdateEventNameRequestDto;
import com.thales.interview.application.service.EventFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EventControllerUnitTest {

    @Mock
    EventFacade eventFacade;
    @InjectMocks
    EventController underTest;

    @Test
    void whenGetEvents_thenFacadeGetEventsIsCalled() {

        //when
        underTest.getEvents();
        //then
        verify(eventFacade, times(1)).getEvents();
    }

    @Test
    void givenNoEvents_whenGetEvents_thenEmptyListIsReturned() {

        //given
        doReturn(new ArrayList<>()).when(eventFacade).getEvents();
        //when
        ResponseEntity<List<EventResponseDto>> events = underTest.getEvents();
        //then
        assertNotNull(events.getBody());
        assertTrue(events.getBody().isEmpty());
    }

    @Test
    void givenEventsExisting_whenGetEvents_thenListOfEventsIsReturned() {

        //given
        EventResponseDto event = EventDtoMother.complete();
        doReturn(List.of(event)).when(eventFacade).getEvents();
        //when
        ResponseEntity<List<EventResponseDto>> events = underTest.getEvents();
        //then
        assertNotNull(events.getBody());
        assertEquals(1, events.getBody().size());
        assertEquals(event, events.getBody().get(0));
    }

    @Test
    void givenRandomId_whenGetEventById_thenFacadeGetEvenyByIdIsCalled() {

        //given
        UUID id = UUID.randomUUID();
        //when
        underTest.getEventById(id);
        //then
        verify(eventFacade, times(1)).getEventById(id);
    }

    @Test
    void givenExistingId_whenGetEventById_thenEventIsReturned() {

        //given
        EventResponseDto event = EventDtoMother.complete();
        doReturn(event).when(eventFacade).getEventById(event.id());
        //when
        ResponseEntity<EventResponseDto> returnedEvent = underTest.getEventById(event.id());
        //then
        assertNotNull(returnedEvent.getBody());
        assertEquals(event, returnedEvent.getBody());

    }

    @Test
    void whenDeleteEvents_thenFacadeDeleteEventsIsCalled() {

        //when
        underTest.deleteEvents();
        //then
        verify(eventFacade, times(1)).deleteEvents();
    }

    @Test
    void givenRandomId_whenDeleteEventById_thenFacadeDeleteEventByIdIsCalled() {

        //given
        UUID id = UUID.randomUUID();
        //when
        underTest.deleteEventById(id);
        //then
        verify(eventFacade, times(1)).deleteEventById(id);
    }

    @Test
    void givenCreateEventsRequest_whenCreateEvents_thenFacadeCreateEventsIsCalled() {

        //given
        CreateEventsRequestDto createEventsRequest = EventDtoMother.completeCreateEventsRequestDto();
        //when
        underTest.createEvents(createEventsRequest);
        //then
        verify(eventFacade, times(1)).createEvents(createEventsRequest);
    }

    @Test
    void givenUpdateEventNameRequest_whenUpdateEventName_thenFacadeUpdateEventNameIsCalled() {

        //given
        UpdateEventNameRequestDto requestDto = EventDtoMother.completeUpdateNameRequestDto();
        //when
        underTest.updateEventName(requestDto);
        //then
        verify(eventFacade, times(1)).updateEventName(requestDto);
    }

    @Test
    void givenUpdateEventDateRequest_whenUpdateEventDate_thenFacadeUpdateEventDateIsCalled() {

        //given
        UpdateEventDateRequestDto requestDto = EventDtoMother.completeUpdateDateRequestDto();
        //when
        underTest.updateEventDate(requestDto);
        //then
        verify(eventFacade, times(1)).updateEventDate(requestDto);
    }
}
