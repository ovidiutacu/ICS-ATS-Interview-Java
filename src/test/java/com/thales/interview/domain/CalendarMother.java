package com.thales.interview.domain;

import com.thales.interview.domain.model.Calendar;

import java.util.UUID;

public class CalendarMother {

    public static Calendar complete() {

        return Calendar.builder()
                .id(UUID.randomUUID())
                .name("Complete Calendar")
                .description("This is a complete calendar")
                .build();
    }
}
