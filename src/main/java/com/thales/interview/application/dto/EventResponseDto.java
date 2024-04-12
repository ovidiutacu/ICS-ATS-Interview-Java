package com.thales.interview.application.dto;

import com.thales.interview.domain.model.EventType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record EventResponseDto(
        UUID id,
        EventType type,
        String name,
        String description,
        LocalDate date,
        UUID calendarId
) {
}
