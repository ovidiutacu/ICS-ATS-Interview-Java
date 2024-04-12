package com.thales.interview.infra.mapper;

import com.thales.interview.domain.model.Event;
import com.thales.interview.infra.entity.EventEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventModelEntityMapper {

    public static EventEntity toEntity(Event model) {

        if (model == null) {
            return null;
        }

        return EventEntity.builder()
                .id(model.id())
                .type(model.type())
                .name(model.name())
                .description(model.description())
                .date(model.date())
                .calendarId(model.calendarId())
                .build();
    }

    public static Event toModel(EventEntity entity) {

        if (entity == null) {
            return null;
        }

        return Event.builder()
                .id(entity.getId())
                .type(entity.getType())
                .name(entity.getName())
                .description(entity.getDescription())
                .date(entity.getDate())
                .calendarId(entity.getCalendarId())
                .build();
    }
}
