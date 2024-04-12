package com.thales.interview.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record Event(
        UUID id,
        EventType type,
        String name,
        String description,
        LocalDate date,
        UUID calendarId
) {
}
