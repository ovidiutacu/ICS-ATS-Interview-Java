package com.thales.interview.infra;

import com.thales.interview.infra.entity.CalendarEntity;

import java.util.UUID;

public class CalendarEntityMother {

    public static CalendarEntity complete() {

        return CalendarEntity.builder()
                .id(UUID.randomUUID())
                .name("Complete Calendar Entity")
                .description("This is a complete calendar entity")
                .build();
    }
}
