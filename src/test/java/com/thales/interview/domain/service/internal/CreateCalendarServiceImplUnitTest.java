package com.thales.interview.domain.service.internal;

import com.thales.interview.domain.CalendarMother;
import com.thales.interview.domain.exception.ElementAlreadyExistingException;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.domain.repository.CalendarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreateCalendarServiceImplUnitTest {

    @Mock
    CalendarRepository calendarRepository;
    @InjectMocks
    CreateCalendarServiceImpl underTest;

    @Test
    void givenNullCalendar_whenCreateCalendar_thenExceptionIsThrown() {

        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createCalendar(null));
    }

    @Test
    void givenCalendarWithoutName_whenCreateCalendar_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete().toBuilder().name(null).build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createCalendar(calendar));
    }

    @Test
    void givenCalendarWithBlankName_whenCreateCalendar_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete().toBuilder().name("").build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> underTest.createCalendar(calendar));
    }

    @Test
    void givenCalendarNameAlreadyExisting_whenCreateCalendar_thenExceptionIsThrown() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(Optional.of(calendar)).when(calendarRepository).findByName(calendar.name());
        //when & then
        assertThrows(ElementAlreadyExistingException.class, () -> underTest.createCalendar(calendar));
    }

    @Test
    void givenValidCalendar_whenCreateCalendar_thenRepositorySaveIsCalled() {

        //given
        Calendar calendar = CalendarMother.complete();
        doReturn(Optional.empty()).when(calendarRepository).findByName(calendar.name());
        //when
        underTest.createCalendar(calendar);
        //then
        verify(calendarRepository, times(1)).save(calendar);
    }
}
