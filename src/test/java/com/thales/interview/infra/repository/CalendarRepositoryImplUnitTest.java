package com.thales.interview.infra.repository;

import com.thales.interview.domain.CalendarMother;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.infra.CalendarEntityMother;
import com.thales.interview.infra.entity.CalendarEntity;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CalendarRepositoryImplUnitTest {

    @Mock
    CalendarJpaRepository jpaRepository;
    @InjectMocks
    CalendarRepositoryImpl underTest;

    @Test
    void whenFindAll_thenJpaRepositoryFindAllIsCalled() {

        //when
        underTest.findAll();
        //then
        verify(jpaRepository, times(1)).findAll();
    }

    @Test
    void givenNoCalendarsInDatabase_whenFindAll_thenReturnEmptyList() {

        //given
        doReturn(new ArrayList<>()).when(jpaRepository).findAll();
        //when
        List<Calendar> calendars = underTest.findAll();
        //then
        assertTrue(calendars.isEmpty());
    }

    @Test
    void givenCalendarsInDatabase_whenFindAll_thenReturnListOfCalendars() {

        //given
        CalendarEntity calendarEntity = CalendarEntityMother.complete();
        doReturn(List.of(calendarEntity)).when(jpaRepository).findAll();
        //when
        List<Calendar> calendars = underTest.findAll();
        //then
        assertEquals(1, calendars.size());
        assertEquals(calendarEntity.getId(), calendars.get(0).id());
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
    void givenNotExistingId_whenFindById_thenNoCalendarIsReturned() {

        //given
        UUID id = UUID.randomUUID();
        doReturn(Optional.empty()).when(jpaRepository).findById(id);
        //when
        Optional<Calendar> calendar = underTest.findById(id);
        //then
        assertTrue(calendar.isEmpty());
    }

    @Test
    void givenExistingId_whenFindById_thenCalendarIsReturned() {

        //given
        CalendarEntity calendarEntity = CalendarEntityMother.complete();
        UUID id = calendarEntity.getId();
        doReturn(Optional.of(calendarEntity)).when(jpaRepository).findById(id);
        //when
        Optional<Calendar> calendar = underTest.findById(id);
        //then
        assertTrue(calendar.isPresent());
        assertEquals(id, calendar.get().id());
    }

    @Test
    void givenNullName_whenFindByName_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.findByName(null));
    }

    @Test
    void givenRandomName_whenFindByName_thenJpaRepositoryFindByNameIsCalled() {

        //given
        String randomName = UUID.randomUUID().toString();
        //when
        underTest.findByName(randomName);
        //then
        verify(jpaRepository, times(1)).findByName(randomName);
    }

    @Test
    void givenNotExistingName_whenFindByName_thenNoCalendarIsReturned() {

        //given
        String name = UUID.randomUUID().toString();
        doReturn(Optional.empty()).when(jpaRepository).findByName(name);
        //when
        Optional<Calendar> calendar = underTest.findByName(name);
        //then
        assertTrue(calendar.isEmpty());
    }

    @Test
    void givenExistingName_whenFindByName_thenCalendarIsReturned() {

        //given
        CalendarEntity calendarEntity = CalendarEntityMother.complete();
        String name = calendarEntity.getName();
        doReturn(Optional.of(calendarEntity)).when(jpaRepository).findByName(name);
        //when
        Optional<Calendar> calendar = underTest.findByName(name);
        //then
        assertTrue(calendar.isPresent());
        assertEquals(name, calendar.get().name());
    }

    @Test
    void givenNullCalendar_whenSave_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.save(null));
    }

    @Test
    void givenRandomCalendar_whenSave_thenJpaRepositorySaveIsCalled() {

        //given
        Calendar calendar = CalendarMother.complete();
        //when
        underTest.save(calendar);
        //then
        verify(jpaRepository, times(1)).save(any());
    }

    @Test
    void givenCalendar_whenSave_thenCalendarIsSaved() {

        //given
        Calendar calendar = CalendarMother.complete();
        when(jpaRepository.save(any())).thenAnswer((Answer<CalendarEntity>) invocation -> {
            CalendarEntity argument = (CalendarEntity) invocation.getArguments()[0];
            return argument.toBuilder()
                    .id(argument.getId() == null ? UUID.randomUUID() : argument.getId())
                    .build();
        });
        //when
        Calendar savedCalendar = underTest.save(calendar);
        //then
        assertEquals(calendar, savedCalendar);
    }

    @Test
    void givenNullCalendar_whenDelete_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.delete(null));
    }

    @Test
    void givenRandomCalendar_whenDelete_thenJpaRepositoryDeleteIsCalled() {

        //given
        Calendar calendar = CalendarMother.complete();
        //when
        underTest.delete(calendar);
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
