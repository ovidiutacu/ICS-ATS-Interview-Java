package com.thales.interview.application.mapper;

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
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.*;

class EventModelDtoMapperUnitTest {

    @Test
    void givenNullEvent_whenMappedToResponseDto_thenNull() {

        //when & then
        assertNull(EventModelDtoMapper.toDto(null));
    }

    @Test
    void givenEvent_thenMappedToResponseDto_thenMappedCorrectly() {

        //given
        Event event = EventMother.complete();
        //when
        EventResponseDto responseDto = EventModelDtoMapper.toDto(event);
        //then
        assertNotNull(responseDto);
        assertEquals(event.id(), responseDto.id());
        assertEquals(event.type(), responseDto.type());
        assertEquals(event.name(), responseDto.name());
        assertEquals(event.description(), responseDto.description());
        assertEquals(event.date(), responseDto.date());
        assertEquals(event.calendarId(), responseDto.calendarId());
    }

    @Test
    void givenNullCreateRequestDto_whenMappedToModel_thenNull() {

        //when & then
        assertNull(EventModelDtoMapper.toModel(null));
    }

    @Test
    void givenCreateRequestDtoWithoutStartDate_whenMappedToModel_thenExceptionIsThrown() {

        //given
        CreateEventsRequestDto dto = EventDtoMother.completeCreateEventsRequestDto().toBuilder()
                .startDate(null)
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> EventModelDtoMapper.toModel(dto));
    }

    @Test
    void givenCreateRequestDtoWithoutEndDate_whenMappedToModel_thenExceptionIsThrown() {

        //given
        CreateEventsRequestDto dto = EventDtoMother.completeCreateEventsRequestDto().toBuilder()
                .endDate(null)
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> EventModelDtoMapper.toModel(dto));
    }

    @Test
    void givenCreateRequestDtoWithEndDateSmallerThanEndDate_whenMappedToModel_thenExceptionIsThrown() {

        //given
        CreateEventsRequestDto dto = EventDtoMother.completeCreateEventsRequestDto().toBuilder()
                .endDate(LocalDate.now().plusDays(2))
                .startDate(LocalDate.now().plusDays(3))
                .build();
        //when & then
        assertThrows(IllegalArgumentException.class, () -> EventModelDtoMapper.toModel(dto));
    }

    @Test
    void givenCreateRequestDto_whenMappedToModel_thenMappedCorrectly() {

        //given
        CreateEventsRequestDto dto = EventDtoMother.completeCreateEventsRequestDto();
        //when
        CreateEventsRequest model = EventModelDtoMapper.toModel(dto);
        //then
        assertEquals(dto.name(), model.name());
        assertEquals(dto.type(), model.type());
        assertEquals(dto.description(), model.description());
        assertEquals(DAYS.between(dto.startDate(), dto.endDate()) + 1, model.dates().size());
        assertEquals(dto.calendarId(), model.calendarId());
    }

    @Test
    void givenNullUpdateDateRequestDto_whenMappedToModel_thenNull() {

        //when & then
        assertNull(EventModelDtoMapper.toUpdateEventDateRequestModel(null));
    }

    @Test
    void givenUpdateDateRequestDto_whenMappedToModel_thenMappedCorrectly() {

        //given
        UpdateEventDateRequestDto dto = EventDtoMother.completeUpdateDateRequestDtoForEvent(UUID.randomUUID());
        //when
        UpdateEventDateRequest model = EventModelDtoMapper.toUpdateEventDateRequestModel(dto);
        //when
        assertNotNull(model);
        assertEquals(dto.eventId(), model.eventId());
        assertEquals(dto.newDate(), model.newDate());
    }

    @Test
    void givenNullUpdateNameRequestDto_whenMappedToModel_thenNull() {

        //when & then
        assertNull(EventModelDtoMapper.toUpdateEventNameRequestModel(null));
    }

    @Test
    void givenUpdateNameRequestDto_whenMappedToModel_thenMappedCorrectly() {

        //given
        UpdateEventNameRequestDto dto = EventDtoMother.completeUpdateNameRequestDtoForEvent(UUID.randomUUID());
        //when
        UpdateEventNameRequest model = EventModelDtoMapper.toUpdateEventNameRequestModel(dto);
        //when
        assertNotNull(model);
        assertEquals(dto.eventId(), model.eventId());
        assertEquals(dto.newName(), model.newName());
    }
}
