package com.thales.interview.infra;

import com.thales.interview.domain.model.EventType;
import com.thales.interview.infra.entity.EventEntity;

import java.time.LocalDate;
import java.util.UUID;

public class EventEntityMother {

    public static EventEntity complete() {

        return EventEntity.builder()
                .id(UUID.randomUUID())
                .type(EventType.DAILY)
                .name("Complete Event Entity")
                .description("This is a complete event entity")
                .date(LocalDate.now())
                .calendarId(UUID.randomUUID())
                .build();
    }
}
