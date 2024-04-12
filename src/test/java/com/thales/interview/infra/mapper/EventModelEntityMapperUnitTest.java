package com.thales.interview.infra.mapper;

import com.thales.interview.domain.EventMother;
import com.thales.interview.domain.model.Event;
import com.thales.interview.infra.EventEntityMother;
import com.thales.interview.infra.entity.EventEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventModelEntityMapperUnitTest {

    @Test
    void givenNullModel_whenMappedToEntity_thenNull() {

        //when & then
        assertNull(EventModelEntityMapper.toEntity(null));
    }

    @Test
    void givenModel_whenMappedToEntity_thenMappedCorrectly() {

        //given
        Event model = EventMother.complete();
        //when
        EventEntity entity = EventModelEntityMapper.toEntity(model);
        //then
        assertNotNull(entity);
        assertEquals(model.id(), entity.getId());
        assertEquals(model.type(), entity.getType());
        assertEquals(model.name(), entity.getName());
        assertEquals(model.description(), entity.getDescription());
        assertEquals(model.date(), entity.getDate());
        assertEquals(model.calendarId(), entity.getCalendarId());
    }

    @Test
    void givenNullEntity_whenMappedToModel_thenNull() {

        //when & then
        assertNull(EventModelEntityMapper.toModel(null));
    }

    @Test
    void givenEntity_whenMappedToModel_thenMappedCorrectly() {

        //given
        EventEntity entity = EventEntityMother.complete();
        //when
        Event model = EventModelEntityMapper.toModel(entity);
        //then
        assertNotNull(model);
        assertEquals(entity.getId(), model.id());
        assertEquals(entity.getType(), model.type());
        assertEquals(entity.getName(), model.name());
        assertEquals(entity.getDescription(), model.description());
        assertEquals(entity.getDate(), model.date());
        assertEquals(entity.getCalendarId(), model.calendarId());
    }
}
