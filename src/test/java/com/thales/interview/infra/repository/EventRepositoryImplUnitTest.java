package com.thales.interview.infra.repository;

import com.thales.interview.domain.EventMother;
import com.thales.interview.domain.model.Event;
import com.thales.interview.infra.EventEntityMother;
import com.thales.interview.infra.entity.EventEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EventRepositoryImplUnitTest {

    @Mock
    EventJpaRepository jpaRepository;
    @InjectMocks
    EventRepositoryImpl underTest;

    @Test
    void whenFindAll_thenJpaRepositoryFindAllIsCalled() {

        //when
        underTest.findAll();
        //then
        verify(jpaRepository, times(1)).findAll();
    }

    @Test
    void givenNoEventsInDatabase_whenFindAll_thenReturnEmptyList() {

        //given
        doReturn(new ArrayList<>()).when(jpaRepository).findAll();
        //when
        List<Event> events = underTest.findAll();
        //then
        assertTrue(events.isEmpty());
    }

    @Test
    void givenEventsInDatabase_whenFindAll_thenReturnListOfEvents() {

        //given
        EventEntity eventEntity = EventEntityMother.complete();
        doReturn(List.of(eventEntity)).when(jpaRepository).findAll();
        //when
        List<Event> events = underTest.findAll();
        //then
        assertEquals(1, events.size());
        assertEquals(eventEntity.getId(), events.get(0).id());
    }

    @Test
    void givenNullId_whenFindById_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.findById(null));
    }

    @Test
    void givenRandomId_whenFindById_thenJpaRepositoryFindByIdIsCalled() {

        //given
        UUID randomId = UUID.randomUUID();
        //when
        underTest.findById(randomId);
        //then
        verify(jpaRepository, times(1)).findById(randomId);
    }

    @Test
    void givenNotExistingId_whenFindById_thenNoEventIsReturned() {

        //given
        UUID id = UUID.randomUUID();
        doReturn(Optional.empty()).when(jpaRepository).findById(id);
        //when
        Optional<Event> event = underTest.findById(id);
        //then
        assertTrue(event.isEmpty());
    }

    @Test
    void givenExistingId_whenFindById_thenEventIsReturned() {

        //given
        EventEntity eventEntity = EventEntityMother.complete();
        UUID id = eventEntity.getId();
        doReturn(Optional.of(eventEntity)).when(jpaRepository).findById(id);
        //when
        Optional<Event> event = underTest.findById(id);
        //then
        assertTrue(event.isPresent());
        assertEquals(id, event.get().id());
    }

    @Test
    void givenNullCalendarId_whenFindByCalendarId_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.findByCalendarId(null));
    }

    @Test
    void givenRandomCalendarId_whenFindByCalendarId_thenJpaRepositoryFindByCalendarIdIsCalled() {

        //given
        UUID randomCalendarId = UUID.randomUUID();
        //when
        underTest.findByCalendarId(randomCalendarId);
        //then
        verify(jpaRepository, times(1)).findByCalendarId(randomCalendarId);
    }

    @Test
    void givenNotExistingCalendarId_whenFindByCalendarId_thenNoEventIsReturned() {

        //given
        UUID calendarId = UUID.randomUUID();
        doReturn(new ArrayList<>()).when(jpaRepository).findByCalendarId(calendarId);
        //when
        List<Event> events = underTest.findByCalendarId(calendarId);
        //then
        assertTrue(events.isEmpty());
    }

    @Test
    void givenExistingCalendarId_whenFindByCalendarId_thenEventsAreReturned() {

        //given
        EventEntity eventEntity = EventEntityMother.complete();
        UUID calendarId = eventEntity.getCalendarId();
        doReturn(List.of(eventEntity)).when(jpaRepository).findByCalendarId(calendarId);
        //when
        List<Event> events = underTest.findByCalendarId(calendarId);
        //then
        assertEquals(1, events.size());
        assertEquals(eventEntity.getCalendarId(), events.get(0).calendarId());
    }

    @Test
    void givenNullEvent_whenSave_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.save(null));
    }

    @Test
    void givenRandomEvent_whenSave_thenJpaRepositorySaveIsCalled() {

        //given
        Event event = EventMother.complete();
        //when
        underTest.save(event);
        //then
        verify(jpaRepository, times(1)).save(any());
    }

    @Test
    void givenEvent_whenSave_thenEventIsSaved() {

        //given
        Event event = EventMother.complete();
        when(jpaRepository.save(any())).thenAnswer((Answer<EventEntity>) invocation -> {
            EventEntity argument = (EventEntity) invocation.getArguments()[0];
            return argument.toBuilder()
                    .id(argument.getId() == null ? UUID.randomUUID() : argument.getId())
                    .build();
        });
        //when
        Event savedEvent = underTest.save(event);
        //then
        assertEquals(event, savedEvent);
    }

    @Test
    void givenNullEvent_whenDelete_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.delete(null));
    }

    @Test
    void givenRandomEvent_whenDelete_thenJpaRepositoryDeleteIsCalled() {

        //given
        Event event = EventMother.complete();
        //when
        underTest.delete(event);
        //then
        verify(jpaRepository, times(1)).delete(any());
    }

    @Test
    void whenDeleteAll_thenJpaRepositoryDeleteAllIsCalled() {

        //when
        underTest.deleteAll();
        //then
        verify(jpaRepository, times(1)).deleteAll();
    }
}
