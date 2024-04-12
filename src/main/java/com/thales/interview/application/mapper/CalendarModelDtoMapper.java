package com.thales.interview.application.mapper;

import com.thales.interview.application.dto.CalendarResponseDto;
import com.thales.interview.application.dto.CreateCalendarRequestDto;
import com.thales.interview.domain.model.Calendar;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalendarModelDtoMapper {

    public static Calendar toModel(CreateCalendarRequestDto dto) {

        if (dto == null) {
            return null;
        }

        return Calendar.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
    }

    public static CalendarResponseDto toDto(Calendar model) {

        if (model == null) {
            return null;
        }

        return CalendarResponseDto.builder()
                .id(model.id())
                .name(model.name())
                .description(model.description())
                .events(new ArrayList<>())
                .build();
    }
}
