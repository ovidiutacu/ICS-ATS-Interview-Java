package com.thales.interview.application.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
public record CalendarResponseDto(
        UUID id,
        String name,
        String description,
        List<EventResponseDto> events
) {
}
