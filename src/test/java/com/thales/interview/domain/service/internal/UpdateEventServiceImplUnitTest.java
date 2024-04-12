package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.EventMother;
import com.thales.interview.domain.model.Event;
import com.thales.interview.domain.model.UpdateEventDateRequest;
import com.thales.interview.domain.model.UpdateEventNameRequest;
import com.thales.interview.domain.repository.EventRepository;
import com.thales.interview.domain.service.ReadEventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UpdateEventServiceImplUnitTest {

    @Mock
    ReadEventService readEventService;
    @Mock
    EventRepository eventRepository;
    @InjectMocks
    UpdateEventServiceImpl underTest;

    @Test
    void givenNullRequest_whenUpdateEventDate_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.updateEventDate(null));
    }

    @Test
    void givenRequestWithoutId_whenUpdateEventDate_thenExceptionIsThrown() {

        //given
        UpdateEventDateRequest request = EventMother.completeUpdateDateRequestForEvent(null);
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.updateEventDate(request));
    }

    @Test
    void givenRequestWithoutNewDate_whenUpdateEventDate_thenExceptionIsThrown() {

        //given
        UpdateEventDateRequest request = EventMother.completeUpdateDateRequestForEvent(UUID.randomUUID()).toBuilder()
                .newDate(null)
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.updateEventDate(request));
    }

    @Test
    void givenRequestWithNewDateAsCurrentDate_whenUpdateEventDate_thenExceptionIsThrown() {

        //given
        UpdateEventDateRequest request = EventMother.completeUpdateDateRequestForEvent(UUID.randomUUID()).toBuilder()
                .newDate(LocalDate.now())
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.updateEventDate(request));
    }

    @Test
    void givenRequestWithNewDateInThePast_whenUpdateEventDate_thenExceptionIsThrown() {

        //given
        UpdateEventDateRequest request = EventMother.completeUpdateDateRequestForEvent(UUID.randomUUID()).toBuilder()
                .newDate(LocalDate.now().minusDays(1))
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.updateEventDate(request));
    }

    @Test
    void givenRequestForNotExistingEvent_whenUpdateEventDate_thenExceptionIsThrown() {

        //given
        doThrow(NoSuchElementException.class).when(readEventService).getEventById(any());
        UpdateEventDateRequest request = EventMother.completeUpdateDateRequestForEvent(UUID.randomUUID());
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.updateEventDate(request));
    }

    @Test
    void givenValidRequest_whenUpdateEventDate_thenEventIsUpdated() {

        //given
        Event event = EventMother.complete();
        doReturn(event).when(readEventService).getEventById(event.id());
        UpdateEventDateRequest request = EventMother.completeUpdateDateRequestForEvent(event.id());
        when(eventRepository.save(any())).thenAnswer((Answer<Event>) invocation -> (Event) invocation.getArguments()[0]);
        //when
        Event updatedEvent = underTest.updateEventDate(request);
        //then
        Event expectedUpdatedEvent = event.toBuilder().date(request.newDate()).build();
        assertEquals(expectedUpdatedEvent, updatedEvent);
    }

    //
    @Test
    void givenNullRequest_whenUpdateEventName_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.updateEventName(null));
    }

    @Test
    void givenRequestWithoutId_whenUpdateEventName_thenExceptionIsThrown() {

        //given
        UpdateEventNameRequest request = EventMother.completeUpdateNameRequestForEvent(null);
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.updateEventName(request));
    }

    @Test
    void givenRequestWithoutNewName_whenUpdateEventName_thenExceptionIsThrown() {

        //given
        UpdateEventNameRequest request = EventMother.completeUpdateNameRequestForEvent(UUID.randomUUID()).toBuilder()
                .newName(null)
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.updateEventName(request));
    }

    @Test
    void givenRequestForNotExistingEvent_whenUpdateEventName_thenExceptionIsThrown() {

        //given
        doThrow(NoSuchElementException.class).when(readEventService).getEventById(any());
        UpdateEventNameRequest request = EventMother.completeUpdateNameRequestForEvent(UUID.randomUUID());
        //when & then
        assertThrows(NoSuchElementException.class, () -> underTest.updateEventName(request));
    }

    @Test
    void givenValidRequest_whenUpdateEventName_thenEventIsUpdated() {

        //given
        Event event = EventMother.complete();
        doReturn(event).when(readEventService).getEventById(event.id());
        UpdateEventNameRequest request = EventMother.completeUpdateNameRequestForEvent(event.id());
        when(eventRepository.save(any())).thenAnswer((Answer<Event>) invocation -> (Event) invocation.getArguments()[0]);
        //when
        Event updatedEvent = underTest.updateEventName(request);
        //then
        Event expectedUpdatedEvent = event.toBuilder().name(request.newName()).build();
        assertEquals(expectedUpdatedEvent, updatedEvent);
    }
}
