package com.thales.interview.application.mapper;

import com.thales.interview.application.CalendarDtoMother;
import com.thales.interview.application.dto.CalendarResponseDto;
import com.thales.interview.application.dto.CreateCalendarRequestDto;
import com.thales.interview.domain.CalendarMother;
import com.thales.interview.domain.model.Calendar;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalendarModelDtoMapperUnitTest {

    @Test
    void givenNullModel_whenMappedToResponseDto_thenNull() {

        //when & then
        assertNull(CalendarModelDtoMapper.toDto(null));
    }

    @Test
    void givenModel_whenMappedToResponseDto_thenMappedCorrectly() {

        //given
        Calendar model = CalendarMother.complete();
        //when
        CalendarResponseDto responseDto = CalendarModelDtoMapper.toDto(model);
        //then
        assertNotNull(responseDto);
        assertEquals(model.id(), responseDto.id());
        assertEquals(model.name(), responseDto.name());
        assertEquals(model.description(), responseDto.description());
        assertTrue(responseDto.events().isEmpty());
    }

    @Test
    void givenNullCreateRequestDto_thenMappedToModel_thenNull() {

        //when & then
        assertNull(CalendarModelDtoMapper.toModel(null));
    }

    @Test
    void givenCreateRequestDto_thenMappedToModel_thenMappedCorrectly() {

        //given
        CreateCalendarRequestDto createRequestDto = CalendarDtoMother.completeCreateCalendarRequest();
        //when
        Calendar calendar = CalendarModelDtoMapper.toModel(createRequestDto);
        //then
        assertNotNull(calendar);
        assertEquals(createRequestDto.name(), calendar.name());
        assertEquals(createRequestDto.description(), calendar.description());
    }
}
