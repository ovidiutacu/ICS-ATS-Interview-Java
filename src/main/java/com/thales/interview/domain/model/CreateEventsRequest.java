package com.thales.interview.domain.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record CreateEventsRequest(
        String name,
        EventType type,
        String description,
        List<LocalDate> dates,
        UUID calendarId
) {
}
