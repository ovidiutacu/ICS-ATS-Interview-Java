package com.thales.interview.application;

import com.thales.interview.application.dto.CalendarResponseDto;
import com.thales.interview.application.dto.CreateCalendarRequestDto;

import java.util.ArrayList;
import java.util.UUID;

public class CalendarDtoMother {

    public static CreateCalendarRequestDto completeCreateCalendarRequest() {

        return CreateCalendarRequestDto.builder()
                .name("Complete Calendar DTO")
                .description("This is a complete calendar DTO")
                .build();
    }

    public static CalendarResponseDto complete() {

        return CalendarResponseDto.builder()
                .id(UUID.randomUUID())
                .name("Complete Calendar DTO")
                .description("This is a complete calendar DTO")
                .events(new ArrayList<>())
                .build();
    }
}
