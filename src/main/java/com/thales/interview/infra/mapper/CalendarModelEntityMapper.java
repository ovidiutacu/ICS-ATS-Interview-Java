package com.thales.interview.infra.mapper;

import com.thales.interview.domain.model.Calendar;
import com.thales.interview.infra.entity.CalendarEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalendarModelEntityMapper {

    public static CalendarEntity toEntity(Calendar model) {

        if (model == null) {
            return null;
        }

        return CalendarEntity.builder()
                .id(model.id())
                .name(model.name())
                .description(model.description())
                .build();
    }

    public static Calendar toModel(CalendarEntity entity) {

        if (entity == null) {
            return null;
        }

        return Calendar.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
