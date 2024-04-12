package com.thales.interview.infra.mapper;

import com.thales.interview.domain.CalendarMother;
import com.thales.interview.domain.model.Calendar;
import com.thales.interview.infra.CalendarEntityMother;
import com.thales.interview.infra.entity.CalendarEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalendarModelEntityMapperUnitTest {

    @Test
    void givenNullModel_whenMappedToEntity_thenNull() {

        //when & then
        assertNull(CalendarModelEntityMapper.toEntity(null));
    }

    @Test
    void givenModel_whenMappedToEntity_thenMappedCorrectly() {

        //given
        Calendar model = CalendarMother.complete();
        //when
        CalendarEntity entity = CalendarModelEntityMapper.toEntity(model);
        //then
        assertNotNull(entity);
        assertEquals(model.id(), entity.getId());
        assertEquals(model.name(), entity.getName());
        assertEquals(model.description(), entity.getDescription());
    }

    @Test
    void givenNullEntity_whenMappedToModel_thenNull() {

        //when & then
        assertNull(CalendarModelEntityMapper.toModel(null));
    }

    @Test
    void givenEntity_whenMappedToModel_thenMappedCorrectly() {

        //given
        CalendarEntity entity = CalendarEntityMother.complete();
        //then
        Calendar model = CalendarModelEntityMapper.toModel(entity);
        //then
        assertNotNull(model);
        assertEquals(entity.getId(), model.id());
        assertEquals(entity.getName(), model.name());
        assertEquals(entity.getDescription(), model.description());
    }
}
